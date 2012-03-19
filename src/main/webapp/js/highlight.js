function doHighlight(bodyText, searchTerm) {
   var highlightStartTag = "<font style='color:blue; background-color:yellow;'>",
       highlightEndTag = "</font>",
       newText = "",
       i = -1,
       lcSearchTerm = searchTerm.toLowerCase(),
       lcBodyText = bodyText.toLowerCase();

   while (bodyText.length > 0) {
       i = lcBodyText.indexOf(lcSearchTerm, i+1);
       if (i < 0) {
           newText += bodyText;
           bodyText = "";
       }
       else {
           // skip anything inside an HTML tag
           if (bodyText.lastIndexOf(">", i) >= bodyText.lastIndexOf("<", i)) {
           // skip anything inside a <script> block
               if (lcBodyText.lastIndexOf("/script>", i) >= lcBodyText.lastIndexOf("<script", i)) {
                   newText += bodyText.substring(0, i) + highlightStartTag + bodyText.substr(i, searchTerm.length) + highlightEndTag;
                   bodyText = bodyText.substr(i + searchTerm.length);
                   lcBodyText = bodyText.toLowerCase();
                   i = -1;
               } 
            }
       }
   }
   return newText;
}

Event.observe(window, 'load', function() {
    var bodyText = document.body.innerHTML;

    for (var i = 0; i < searchArray.length; i++) {
        bodyText = doHighlight(bodyText, searchArray[i]);
    }
    document.body.innerHTML = bodyText;
})