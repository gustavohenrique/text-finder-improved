package br.com.wdev.plugins;

import hudson.model.Action;
import hudson.model.AbstractProject;

import java.util.List;

import br.com.wdev.Finder;

public class TextFinderProjectAction implements Action {

    public List<Finder> finders;
    
    public AbstractProject project;

    public TextFinderProjectAction(AbstractProject project, List<Finder> finders) {
        this.finders = finders;
        this.project = project;
        //Collections.reverse(this.finders);
    }
    
    public String getIconFileName() {
        return "/plugin/text-finder-improved/images/icon.png";
    }

    public String getDisplayName() {
        return "Text Finder History";
    }

    public String getUrlName() {
        return "text-finder-improved";
    }

}
