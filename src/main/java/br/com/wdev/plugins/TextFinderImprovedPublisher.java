package br.com.wdev.plugins;

import static hudson.Util.fixEmpty;
import hudson.Extension;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Computer;
import hudson.remoting.VirtualChannel;
import hudson.slaves.SlaveComputer;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import br.com.wdev.Constants;
import br.com.wdev.Finder;
import br.com.wdev.XmlParserUtil;

public class TextFinderImprovedPublisher extends Recorder implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public final String includes;

    public final String excludes;
	
    public final String regexp;
	
    public final boolean caseSensitive;
	
    public final boolean checkOnlyConsoleOutput;
	
    public final String buildResult;

    
	@DataBoundConstructor
    public TextFinderImprovedPublisher(String includes, String excludes, String regexp, boolean caseSensitive, String buildResult, boolean checkOnlyConsoleOutput) {
        this.includes = Util.fixNull(includes);
        this.excludes = Util.fixNull(excludes);
        this.regexp = Util.fixNull(regexp);
        this.caseSensitive = caseSensitive;
        this.buildResult = buildResult;
        this.checkOnlyConsoleOutput = checkOnlyConsoleOutput;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    public boolean perform(final AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
    	build.getWorkspace().act(new FileCallable<Boolean>() {
    	    
    	    private XmlParserUtil xmlParserUtil = new XmlParserUtil();
    		
    		public Boolean invoke(File workspace, VirtualChannel channel) throws IOException {
                
                final String SPLIT_SEPARATOR = " ";
                
                Finder finder = new Finder(workspace, includes.split(SPLIT_SEPARATOR), excludes.split(SPLIT_SEPARATOR), regexp);
                finder.setBuildResult(buildResult);
                finder.buildNumber = build.getNumber();
        		finder.checkOnlyConsoleOutput = checkOnlyConsoleOutput;
        		finder.caseSensitive = caseSensitive;
        		
        		finder.findText();
        		
        		File projectDir = getProjectDir(build);
        		List<Finder> finders = xmlParserUtil.fromXml(projectDir);
        		finders.add(finder);
        		xmlParserUtil.toXml(finders, projectDir);
        		
        		build.getActions().add(new FinderAction(build, finder));
        		build.setResult(finder.buildResult);
        		
        		return finder.reports.size() > 0;
    		}

            private File getProjectDir(final AbstractBuild<?, ?> build) {
                File projectDir = build.getProject().getRootDir();
                return new File(projectDir.getAbsolutePath() + Constants.OUTPUT_XML_FILENAME);
            }
    	});
    	
        return true;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        public String getDisplayName() {
            return "Text Finder Improved";
        }

        @Override
        public String getHelpFile() {
            return "/plugin/text-finder/help.html";
        }

        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        public FormValidation doCheckRegexp(@QueryParameter String value) throws IOException, ServletException {
            value = fixEmpty(value);
            if(value==null)
                return FormValidation.ok(); // not entered yet

            try {
                Pattern.compile(value);
                return FormValidation.ok();
            } catch (PatternSyntaxException e) {
                return FormValidation.error(e.getMessage());
            }
        }
    }
    
}
