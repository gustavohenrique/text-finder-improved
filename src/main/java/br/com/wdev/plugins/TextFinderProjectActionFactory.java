package br.com.wdev.plugins;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.TransientProjectActionFactory;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.util.RunList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.wdev.Finder;

//@Extension
public class TextFinderProjectActionFactory extends TransientProjectActionFactory {

    @Override
    public Collection<? extends Action> createFor(AbstractProject project) {
//        File xmlReportFile = new File(project.getRootDir().getPath() + Constants.OUTPUT_XML_FILENAME);
//        List<Finder> finders = new XmlParserUtil().fromXml(xmlReportFile);
//        List<Finder> finders = new ArrayList<Finder>();
        
        List<Action> actions = new ArrayList<Action>();
//        Iterator<? extends AbstractBuild> buildIterator = project.getBuilds().iterator();
//        while (buildIterator.hasNext()) {
//            Action action = buildIterator.next().getAction(TextFinderAction.class);
//            actions.add(action);
//        }
//        
//        actions.add(new TextFinderProjectAction(project, finders));
        
        actions.add(new TextFinderProjectAction(project, null));
        
        return actions;
    }
    
    
}
