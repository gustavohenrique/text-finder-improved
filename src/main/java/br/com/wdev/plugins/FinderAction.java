package br.com.wdev.plugins;

import hudson.model.Action;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import br.com.wdev.Finder;

public class FinderAction implements Action {
    
    public AbstractBuild<?,?> build;
    
    private Finder finder;
    
    public FinderAction(final Run build, final Finder finder) {
        this.build = (AbstractBuild<?, ?>) build;
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
