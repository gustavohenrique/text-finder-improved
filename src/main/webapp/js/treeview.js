function _toggleClass(element, c1, c2) {
     if (element.hasClassName(c1)) {
         element.removeClassName(c1);
         element.addClassName(c2);
     }
 }
 
 function treeview(element) {
     var li_parent = $('li_'+element.id), 
         ul_child = $('ul_'+element.id);
     
     if (ul_child.visible()) {
         ul_child.hide();
         _toggleClass(li_parent, 'lastCollapsable', 'lastExpandable');

         element.addClassName('closed-hitarea');
         element.addClassName('expandable-hitarea');
         element.removeClassName('collapsable-hitarea');
     }
     else {
         ul_child.show();
         _toggleClass(li_parent, 'lastExpandable', 'lastCollapsable');
         
         element.removeClassName('closed-hitarea');
         element.removeClassName('expandable-hitarea');
         element.addClassName('collapsable-hitarea');                     
     }
 }