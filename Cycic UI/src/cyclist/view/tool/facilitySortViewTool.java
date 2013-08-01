package cyclist.view.tool;

import org.puremvc.java.multicore.patterns.mediator.Mediator;
import javafx.scene.image.Image;
import cyclist.view.component.View;
import cyclist.view.tool.view.facilitySortView;

public class facilitySortViewTool implements Tool{
	
	public Image getIcon () {
		return Resources.getIcon("Unknown");
	}
	public String getName(){
		return "FacilitySort";
		
	}
	public View getView(){
		View view=new facilitySortView();
		view.setParam("FacilitySort");
		return view;
	}
	public Mediator getMediator (){
		return null;
	}
}
	
