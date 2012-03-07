package br.com.wdev.plugins;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.TransientProjectActionFactory;
import hudson.model.AbstractProject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.wdev.Constants;
import br.com.wdev.Finder;
import br.com.wdev.XmlParserUtil;

@Extension
public class TextFinderProject extends TransientProjectActionFactory {

    @Override
    public Collection<? extends Action> createFor(AbstractProject project) {
        File xmlReportFile = new File(project.getRootDir().getPath() + Constants.OUTPUT_XML_FILENAME);
        
        List<Finder> finders = new XmlParserUtil().fromXml(xmlReportFile);
        
        List<FinderAction> actions = new ArrayList<FinderAction>();
        actions.add(new FinderProjectAction(project, finders));
        return actions;
    }
    
    
}
