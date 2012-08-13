// Nodes Functions //
var Fnodes = [],
    Tnodes= [],
    links = [],
    Tlinks = [];

function addParentCircle(){
	if(window.TYPE ==="green"){	
		Fnodes.push(facilities[nameStore[window.NAME]]['circle']);
		Tnodes.push(facilities[nameStore[window.NAME]]['circle']);
		
		for( k = 0; k < Fnodes.length; k ++){
			if(Fnodes[k].name === window.NAME){
				var facilityNamePass = k;
			}
		}

		if(facilities[nameStore[window.NAME]]['inMarket']){
			for( i = 0; i < Fnodes.length; i ++){
				if(toTitleCase(Fnodes[i].name) === toTitleCase(facilities[window.NAME]['inMarket'])){
					var marketNamePass = i;
				}
			}
	  		links.push({source: Fnodes[facilityNamePass], target: Fnodes[marketNamePass]});
	  		Tlinks.push({source: Fnodes[facilityNamePass], target: Fnodes[marketNamePass]});
	  	}
	  	if(facilities[nameStore[window.NAME]]['outMarket']){
			for( j = 0; j < Fnodes.length; j ++){
				if(toTitleCase(Fnodes[j].name) === toTitleCase(facilities[nameStore[window.NAME]]['outMarket'])){
					var marketNamePass = j;
				}
			}
	  		links.push({source: Fnodes[facilityNamePass], target: Fnodes[marketNamePass]});
	  		Tlinks.push({source: Fnodes[facilityNamePass], target: Fnodes[marketNamePass]});
	  	}			  	
	}
	if(window.TYPE ==="steelblue"){
		Fnodes.push(MARKETS[nameStore[window.NAME]]['circle']);
		Tnodes.push(MARKETS[nameStore[window.NAME]]['circle']);
	}
	
  	var Fnode = vis.selectAll("g.node")
  		.data(Tnodes, function(d) {return d.name;});
  		
  	if(window.TYPE === "green"){
  		Fnode.enter().append("svg:g")
	     .attr("class", "node")
		 .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
		 .call(force.drag)
		 .on("dblclick", function(d){if(facilities[d.name]['state']=="open"){hideChildren(d.name)}else{showChildren(d.name)};})
		 /*.on("mouseover", function(d){showChildren(d.name);})
		 .on("mouseout", function(d){hideChildren(d.name);})*/
		 .on("click", function(d){openFacilityForm(d.name);})
		Fnode.append("circle")
		 .attr("r", function(d) {return d.size;})
		 .style("fill", function() {return window.TYPE;})
		 .style("stroke", "white")
		 .style("stroke-width", "1.5px")
		Fnode.append("text")
		 .attr("text-anchor", "middle")
		 .attr("dy", "0.2em")
		 .attr("font-size", "12")
		 .text(function(d) {return nameStoreBack[d.name];})
  	}
  	if(window.TYPE === "steelblue"){
  		Fnode.enter().append("svg:g")
	     .attr("class", "node")
		 .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
		 .call(force.drag)
		 .on("click", function(d){openMarketForm(d.name);})
	    Fnode.append("ellipse")
		 .attr("rx", function(d) {return d.size;})
		 .attr("ry", function(d) {return d.size * 0.75;})
		 .style("fill", function() {return d.color;})
		 .style("stroke", "white")
		 .style("stroke-width", "1.5px")
		Fnode.append("text")
		 .attr("text-anchor", "middle")
		 .attr("dy", "0.2em")
		 .attr("font-size", "12")
		 .text(function(d) {return nameStoreBack[d.name];})	 	  		
  	}

	Fnode.append("title")
		.text(function(d) {return nameStoreBack[d.name];})

  	var link = vis.selectAll("line.link")
	      .data(Tlinks, function(d) {return d.source.id + "-" + d.target.id; });
	
	link.enter().insert("svg:line", "g.node")
	      .attr("class", "link")
      
    force.start();
    Tnodes.splice(0, Tnodes.length);
    Tlinks.splice(0, Tlinks.length);
 }
 
function addCloneCircle(){
 	var test = false;
 	for(i = 0; i < Fnodes.length; i++){
		if(Fnodes[i]['name'] == nameStore[window.NAME]){
			test = true;
		}
 	}
	if(test == false){
		for(i = 0; i < Fnodes.length; i++){
			if(Fnodes[i]['name'] === window.PARENT){
				var facNamePass = i;
			}
		}
		Fnodes.push(facilities[nameStore[window.NAME]]['circle']);
		var test1 = false;
		for(i = 0; i < Fnodes[facNamePass]['children'].length; i++){
			if(Fnodes[facNamePass]['children'][i]['name'] === nameStore[window.NAME]){
				test1 = true;
			}
		}
		if(test1 == false){
			Fnodes[facNamePass]['children'].push(facilities[nameStore[window.NAME]]['circle']);
		}
		Tnodes.push(facilities[nameStore[window.NAME]]['circle']);
		
  		links.push({source: Fnodes[facNamePass], target: Fnodes[Fnodes.length-1]});
  		Tlinks.push({source: Fnodes[facNamePass], target: Fnodes[Fnodes.length-1]});	
  				
		Fnode = vis.selectAll("g.node")
	  		.data(Tnodes, function(d) {return d.name;});
  		Fnode.enter().append("svg:g")
  		     .attr("class", "node")
  			 .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
  			 .call(force.drag)
  			 .on("click", function(d){openFacilityForm(d.name);})
  		Fnode.append("title")
  			.text(function(d) {return nameStoreBack[d.name];})
		Fnode.append("circle")
			.attr("r", "40")
			.style("fill", function() {return window.TYPE;})
  			.style("stroke", "white")
  			.style("stroke-width", "1.5px")
  			
		Fnode.append("text")
			.attr("text-anchor", "middle")
			.attr("dy", "0.2em")
			.attr("font-size", "10")
			.text(function(d) {return nameStoreBack[d.name];})
	  	
	  	var link = vis.selectAll("line.link")
		      .data(Tlinks, function(d) { return d.source.id + "-" + d.target.id; });
		
		link.enter().insert("svg:line", "g.node")
		      .attr("class", "link")
		      .style("stroke", "gray")
		      .style("stroke-dasharray", "10, 5")
	      
	}
	 	    
	force.start();
    Tnodes.splice(0, Tnodes.length);
    Tlinks.splice(0, Tlinks.length);	
}

function hideChildren(facility){
	/*CLOSE IT */
	//Removing Child links to markets //
	var childNamePass = [];
	var parMarkNamePass = [];
	for(i = 0; i < Fnodes.length; i++){
		if(Fnodes[i]['name'] == [facility]){
			var facNamePass = i;
			for(j = 0; j < Fnodes[i]['children'].length; j++){
				childNamePass.push(Fnodes[i]['children'][j]);
			}
			for(k = 0; k < childNamePass.length; k++){
				for(ii = 0; ii < links.length; ii++){
					if(childNamePass[k]['name'] == links[ii]['source']['name']){
						// Obtaining market links for parent //
						parMarkNamePass.push(links[ii]['target']['name']);
						for(jj = 0; jj < parMarkNamePass.length-1; jj++){
							if(parMarkNamePass[jj] == parMarkNamePass[parMarkNamePass.length-1]){
								parMarkNamePass.pop();
							}
						}
						// Removing child links to markets //
						links.splice(ii, 1);
						ii = ii - 1;
					}
				}
			}
		}
	}		
	/* Finding and removing children nodes */
	for(i = 0; i < Fnodes.length; i++){
		if(Fnodes[i]['name'] == [facility]){
			for(ii = 0; ii < Fnodes[i]['children'].length; ii++){
				for(j = 0; j < Fnodes.length; j++){
					if(Fnodes[j]['name'] == Fnodes[i]['children'][ii]['name']){
						Fnodes.splice(j, 1);
						j = j-1;
					}
				}
			}
		}
	}
	/* Finding and removing children links*/
	for(i = 0; i < links.length; i++){
		if(links[i]['source']['name'] == facilities[[facility]]['Name']){
			if(links[i]['target']['call'] == "child"){
				links.splice(i, 1);
				i = i-1;
			}
		}
	}
	// Removing excess links //
	var link = vis.selectAll("line.link")
		.data(links, function(d) { return d.source.id + "-" + d.target.id; });
	link.exit().remove();
	// Removing the nodes //
	Fnode = vis.selectAll("g.node")
  		.data(Fnodes, function(d) {return d.name;});
  	Fnode.exit().remove();
	// Pushing parent to market links //
	for(i = 0; i < parMarkNamePass.length; i++){
		for(j = 0; j < Fnodes.length; j++){
			if(parMarkNamePass[i] == Fnodes[j]['name']){
				links.push({source: Fnodes[facNamePass], target: Fnodes[j]})
			}
		}
	}		
  	// Updating the links //	
	var link = vis.selectAll("line.link")
		.data(links, function(d) { return d.source.id + "-" + d.target.id; });
	link.enter().insert("svg:line", "g.node")
     	.attr("class", "link")
		
	force.start();
	facilities[[facility]]['state'] = "closed";				
}

function showChildren(facility){
	/*OPEN IT */
	// Removing parent node to market links //
	for(i = 0; i < Fnodes.length; i++){
		if(Fnodes[i]['name'] == nameStore[nameStoreBack[facility]]){
			if(Fnodes[i]['children'].length > 0){
				for(j = 0; j < links.length; j++){
					if(links[j].source['name'] == nameStore[nameStoreBack[facility]]){
						if(links[j].target['call'] == "mark"){
							links.splice(j,1);
							j = j-1;
						}
					}
				}				
			}
		}
	}
	var link = vis.selectAll("line.link")
		.data(links, function(d) { return d.source.id + "-" + d.target.id; });
	link.exit().remove();
	// Returning the parent to child nodes //
	var childNamePass = [];
	Tnodes.splice(0, Tnodes.length);
	for(i = 0; i < Fnodes.length; i++){
		if(Fnodes[i]['name'] == nameStore[nameStoreBack[facility]]){
			for(j = 0; j < Fnodes[i]['children'].length; j++){
				Fnodes.push(Fnodes[i]['children'][j]);
				Tnodes.push(Fnodes[i]['children'][j]);
				links.push({source: Fnodes[i], target: Fnodes[Fnodes.length-1]});
				Tlinks.push({source: Fnodes[i], target: Fnodes[Fnodes.length-1]});
				childNamePass.push(Fnodes[i]['children'][j]['name']);
			}
		}
	}
	var link = vis.selectAll("line.link")
		.data(Tlinks, function(d) { return d.source.id + "-" + d.target.id; });
	link.enter().insert("svg:line", "g.node")
		      .attr("class", "link")
		      .style("stroke", "gray")
		      .style("stroke-dasharray", "10, 5")
	Fnode = vis.selectAll("g.node")
  		.data(Tnodes, function(d) {return d.name;});
	Fnode.enter().append("svg:g")
	     .attr("class", "node")
		 .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; })
		 .call(force.drag)
		 .on("click", function(d){openFacilityForm(d.name);})
	Fnode.append("title")
		.text(function(d) {return d.name;})
	Fnode.append("circle")
		.attr("r", "40")
		.style("fill", function() {return window.TYPE;})
		.style("stroke", "white")
		.style("stroke-width", "1.5px")
		
	Fnode.append("text")
		.attr("text-anchor", "middle")
		.attr("dy", "0.2em")
		.attr("font-size", "10")
		.text(function(d) {return nameStoreBack[d.name];})
	force.start();
	
	Tnodes.splice(0, Tnodes.length);
    Tlinks.splice(0, Tlinks.length);
	
	// Returning the markets //
	var marketNamePass = [];
	for(i = 0; i < childNamePass.length; i++){
		if(facilities[childNamePass[i]]['inMarket']){
			marketNamePass.push(facilities[childNamePass[i]]['inMarket']);
		}
		if(facilities[childNamePass[i]]['outMarket']){
			marketNamePass.push(facilities[childNamePass[i]]['outMarket']);
		}
		for(ii = 0; ii < Fnodes.length; ii++){
			if(childNamePass[i] == Fnodes[ii]['name']){
				var facNamePass = ii;
			}
		}
		for(j = 0; j < marketNamePass.length; j++){
			for(k = 0; k < Fnodes.length; k++){
				if(Fnodes[k]['name'] == marketNamePass[j]){
					links.push({source: Fnodes[facNamePass], target: Fnodes[k]});
					Tlinks.push({source: Fnodes[facNamePass], target: Fnodes[k]});
				}				
			}
		}
		marketNamePass.splice(0,marketNamePass.length);	
	}
  		
	var link = vis.selectAll("line.link")
		.data(Tlinks, function(d) { return d.source.id + "-" + d.target.id; });
	link.enter().insert("svg:line", "g.node")
		      .attr("class", "link")
		      .style("stroke", "black")
			
	Tnodes.splice(0, Tnodes.length);
    Tlinks.splice(0, Tlinks.length);
    facilities[[facility]]['state'] = "open"
	force.start();
}