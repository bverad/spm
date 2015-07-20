YUI().use("node","frame", function(Y){
    var frame = new Y.Frame({
        title:"foo",
        container: "#bork",
        content: "<hr/><b>loading</b><hr/>",
        id: "foopynoopy",
        designMode: false,
        use: ['substitute', 'node', 'selector-css3', 'selection', 'stylesheet'],
        name: "yuiframe", //bug: not used?
        extracss: "hr {color: blue;}"
    });
    
    frame.after("ready", function(){
        Y.log("ready");
        
        //modify something inside the frame
        var fi = frame.getInstance();
        fi.one("b").set("innerHTML", "loaded!").setStyle("color", "green"); 
  
        // set a frame attribute
        frame._iframe.setStyle("height", 100);      
    })
    frame.render();
    

    Y.one("#test").on("click", function(e){
        var fi = frame.getInstance();
        
        //make another change
        
        fi.one("b").set("innerHTML", "clicked").setStyle("color","red")
        

            
    })
    
    
    
    
})