package br.com.wdev.plugins;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import br.com.wdev.Finder;

public class FinderAction implements Action {
    
    public final AbstractBuild<?,?> build;
    
    private Finder finder;
    
    public FinderAction(final AbstractBuild build, final Finder finder) {
        this.build = build;
        this.finder = finder;
    }
    
    public Finder getFinder() {
        return finder;
    }
    
    public String getIconFileName() {
        return "/plugin/text-finder-improved/images/icon.png";
    }   

    public String getDisplayName() {
        return "Text Finder Relatório";
    }

    public String getUrlName() {
        return "text-finder-improved";
    }

}
