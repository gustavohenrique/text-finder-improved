package br.com.wdev.plugins;

import static hudson.Util.fixEmpty;
import hudson.Extension;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.util.FormValidation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import br.com.wdev.Constants;
import br.com.wdev.Finder;
import br.com.wdev.XmlParserUtil;

public class TextFinderRecorder extends Recorder implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public final String includes;

    public final String excludes;
	
    public final String words;
    
    public final String regexp;
	
    public final boolean caseSensitive;
	
    public final boolean checkOnlyConsoleOutput;
	
    public final String buildResult;

    
	@DataBoundConstructor
    public TextFinderRecorder(String includes, String excludes, String words, String regexp, boolean caseSensitive, String buildResult, boolean checkOnlyConsoleOutput) {
        this.includes = Util.fixNull(includes);
        this.excludes = Util.fixNull(excludes);
        this.words = Util.fixNull(words);
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
                
                final String SPLIT_SEPARATOR = ",";
                
                Finder finder = new Finder(workspace);
                finder.includes = includes.split(SPLIT_SEPARATOR);
                finder.excludes = excludes.split(SPLIT_SEPARATOR);
                finder.words = words.split(SPLIT_SEPARATOR);
                finder.regexp = regexp;
                finder.setBuildResult(buildResult);
                finder.buildNumber = build.getNumber();
        		finder.checkOnlyConsoleOutput = checkOnlyConsoleOutput;
        		finder.caseSensitive = caseSensitive;
        		
        		finder.findText();
        		
        		//saveFinderInExternalXml(build, finder);
        		
        		build.getActions().add(new TextFinderAction(build, finder));
        		build.setResult(finder.buildResult);
        		
        		return finder.reports.size() > 0;
    		}

    		/*
            private void saveFinderInExternalXml(final AbstractBuild<?, ?> build, Finder finder) {
                File projectDir = getProjectDir(build);
        		List<Finder> finders = xmlParserUtil.fromXml(projectDir);
        		finders.add(finder);
        		xmlParserUtil.toXml(finders, projectDir);
            }

            private File getProjectDir(final AbstractBuild<?, ?> build) {
                File projectDir = build.getProject().getRootDir();
                return new File(projectDir.getAbsolutePath() + Constants.OUTPUT_XML_FILENAME);
            }
            */
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
            return "/plugin/text-finder-improved/help.html";
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
