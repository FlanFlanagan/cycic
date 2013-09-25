package cyclist.view.tool.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import cyclist.model.vo.DnD;
import cyclist.model.vo.Facility;
import javafx.scene.control.*;
import cyclist.view.component.View;

public class facilitySortView extends View {
	static BorderPane pane= new BorderPane();
	static Pane display=new Pane();
	static ArrayList<Object> structure=new ArrayList<>();
	
	public facilitySortView (){
		super();
		setWidth(780);
		setMaxWidth(900);
		setMinWidth(780);
		setHeight(300);
		setMaxHeight(300);
		init ();
	}
	private void init (){
		//initialize most variables
		final Text list=new Text ();
		HBox topBox= new HBox();
		final HBox searchBox=new HBox();
		//Label text= new Label();
		final Label number=new Label();
		final Label type=new Label();
		final Slider numberShowen=new Slider(0,0,0);
		final VBox criteria = new VBox();
		final Button add= new Button();
		final VBox addMore= new VBox();
		final ComboBox<String> facilityList=new ComboBox<String>();
		//final ArrayList<String> structure=new ArrayList<String>();
		final int index=0;
		final ArrayList<Object>data=new ArrayList<>();
		final ArrayList<Object> filt=new ArrayList<>();
		//variables default info
		//criteria.setMinWidth(200);
		criteria.setSpacing(10);
		//pane.setPrefSize(1000,900);
		display.setMinSize(250,150);
		searchBox.setSpacing(10);
		 number.setText("number of nodes shown:  " );
		 number.setVisible(false);
		searchBox.setMaxWidth(600);
		facilityList.setVisibleRowCount(5);
		add.setText("add Criteria");
		final Button start=new Button();
		start.setText("Start");
		criteria.setStyle("-fx-border-color: black;");
		criteria.setPrefHeight(getHeight());
		searchBox.setMaxHeight(35);
		searchBox.setStyle("-fx-border-color: black;");
		addMore.setSpacing(10);
		addMore.setId("");
		
		topBox.getChildren().add(searchBox);
		topBox.getChildren().add(criteria);
		//top searchbox information
		searchBox.getChildren().add(facilityList);
		//searchBox.getChildren().add(text);
		searchBox.getChildren().add(numberShowen);
		searchBox.getChildren().add(number);
		searchBox.getChildren().add(type);
		//searchBox.getChildren().add(add);
		//searchBox.getChildren().add(criteria);
		topBox.setSpacing(50);
		//criteria.setPrefHeight(pane.getHeight());
		//right criteria column 
		
		//drop down for all parent nodes
		for(int i =0; i < dataArrays.FacilityNodes.size(); i++){
				facilityList.getItems().add(dataArrays.FacilityNodes.get(i).getId());
			}
		
		//read chosen facility parent node, update slider bar, type, and searchlist 
		facilityList.valueProperty().addListener(new ChangeListener<String>(){
			
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				//find the location of chosen node in the data list
				criteria.getChildren().clear();
				structure.clear();
				
				data.clear();
				int i=0;
				while (!(dataArrays.FacilityNodes.get(i).getId().equals(newValue))){
					i++;
				}
				facilityList.setId(""+i);
				//update data array
				for (int ii=0;ii<dataArrays.FacilityNodes.get(i).facilityStructure.size();ii++){
					ArrayList<Object>element=new ArrayList<>();
					for (int iii=0;iii<dataArrays.FacilityNodes.get(i).childrenList.size();iii++){
						element.add(dataArrays.FacilityNodes.get(i).childrenList.get(iii).facilityData.get(ii));
					}
					data.add(element);
				}
				
				//update information in searchbox
				type.setText("Type: "+dataArrays.FacilityNodes.get(i).facilityType);
				numberShowen.setMax(dataArrays.FacilityNodes.get(i).childrenList.size());
				numberShowen.setShowTickMarks(true);
				numberShowen.setShowTickLabels(true);
				numberShowen.setMinorTickCount(0);
				numberShowen.setMajorTickUnit(1);
				numberShowen.setSnapToTicks(true);
				numberShowen.valueProperty().addListener(new ChangeListener<Number>(){
		            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
		            		number.setVisible(true);
		                   number.setText("number of nodes shown:" +Integer.toString(new_val.intValue()));
		            	//	number.setText("number of nodes showen:   " +new_val);
		                  //System.out.println(filt);
		            }
		        });
			//	add.setId(Integer.toString(i));
				if (!(addMore.getId().equals(newValue))){
					addMore.setId(newValue);
					addMore.getChildren().clear();
				}
				
				formSort(dataArrays.FacilityNodes.get(i).facilityStructure,data,structure);
				//System.out.println(data);
				//System.out.println(dataArrays.FacilityNodes.get(i).facilityStructure);
				//System.out.println(structure);
				criteria.getChildren().add(add);
				criteria.getChildren().add(addMore);
				criteria.getChildren().add(start);

		
			}
		});
		
		//sliderbar listener, change the value on the right ##change the childnode displayed
		numberShowen.valueProperty().addListener(new ChangeListener<Number>(){
            public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
                    number.setText(Integer.toString(new_val.intValue()));
            }
        });

		//add button function. add addmore and filter button
		add.setOnMouseClicked(new EventHandler <MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				final HBox detailBox=new HBox();
				detailBox.setSpacing(5);
				ComboBox<String> detail=new ComboBox<String>();
				//data
				final ArrayList<ArrayList> listed=new ArrayList<>();
				//stucture for the term
				final ArrayList<ArrayList> struc=new ArrayList<>();
				//
				final ArrayList<Object> order=new ArrayList<>();
				for (int k=0;k<structure.size();k++){
					ArrayList<ArrayList>detailInfo=(ArrayList<ArrayList>)structure.get(k);
					detail.getItems().add((String) detailInfo.get(0).get(0));
					order.add((String) detailInfo.get(0).get(0));
					listed.add(detailInfo.get(1));
					struc.add(detailInfo.get(0));
				}
				detail.setVisibleRowCount(7);
				detailBox.getChildren().add(detail);
				addMore.getChildren().add(detailBox);

		//		System.out.println(dataArrays.FacilityNodes.get(0).facilityData);
		//		System.out.println(data);
				detail.valueProperty().addListener(new ChangeListener <String>(){

					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String oldValue, final String newValue) {
						// TODO Auto-generated method stub
						int i=0;
						while (!(order.get(i).equals(newValue))){
							i++;
						}
						// if the type of choice is string, add a combobox to detailbox (Hbox)
						if (struc.get(i).get(2).equals("String")){
							if (detailBox.getChildren().size()>=1){
								detailBox.getChildren().remove(1);
							}
							final ComboBox <String> information=new ComboBox<>();
							for (int ii=0;ii<((ArrayList<Object>) listed.get(i)).size();ii++){
								ArrayList<ArrayList>newList=(ArrayList<ArrayList>)listed.get(i);
								if(!(information.getItems().contains(newList.get(ii).get(0)))){
									information.getItems().add(newList.get(ii).get(0).toString());
								}
								
							}
							detailBox.getChildren().add(information);
							information.valueProperty().addListener(new ChangeListener <String>(){

								@Override
								public void changed(ObservableValue<? extends String> arg,
										String oldProperty, String newProperty) {
									ArrayList <Object>subfilt=new ArrayList<>();
									subfilt.add(newValue);
									subfilt.add(newProperty);
									filt.add(subfilt);	
								}
							});
						}
						else {
							VBox range=new VBox();
							HBox lB=new HBox();
							HBox UB =new HBox();
							Text lbNum=new Text();
							Text uBNum=new Text();
							Button confirm=new Button();
							confirm.setText("confirm");
							lbNum.setText("LB");
							uBNum.setText("UB");
							lB.getChildren().add(lbNum);
							final TextField lBValue=new TextField();
							lBValue.setPromptText("Number Only");
							final TextField uBValue=new TextField();
							uBValue.setPromptText("Number Only");

							lB.getChildren().add(lBValue);
							UB.getChildren().add(uBNum);
							UB.getChildren().add(uBValue);
							range.getChildren().add(lB);
							range.getChildren().add(UB);
							range.getChildren().add(confirm);
							confirm.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent e) {
									if (!(isInteger(lBValue.getText()))){
										lBValue.setText("PLEASE!!!NUMBERS!!!");
									} else if (!(isInteger(uBValue.getText()))){
										uBValue.setText("PLEASE!!!NUMBERS!!!");
									} else if (Integer.parseInt(lBValue.getText())>Integer.parseInt(uBValue.getText())){
										lBValue.setText("PLEASE ENTER CORRECTLY");
										uBValue.setText("PLEASE ENTER CORRECTLY");
									}else {
										int lowvalue;
										int upvalue;
										lowvalue=Integer.parseInt(lBValue.getText());
										upvalue=Integer.parseInt(uBValue.getText());
										ArrayList <Object>Subfilt=new ArrayList<>();
										Subfilt.add(newValue);
										Subfilt.add(lowvalue);
										Subfilt.add(upvalue);
										filt.add(Subfilt);
									}
								}
							});
							if (detailBox.getChildren().size()>=1){
								detailBox.getChildren().remove(1);
							}
							detailBox.getChildren().add(range);
							//lBValue.getOnAction(new action<>)
							
						}
					}
					
				});
				start.setOnMouseClicked(new EventHandler <MouseEvent>(){

					@Override
					public void handle(MouseEvent event) {
						System.out.println(filt);
						ArrayList<Object> displaylist=new ArrayList<>();
						int order= Integer.parseInt(facilityList.getId());
						ArrayList<ArrayList> struc=new ArrayList<>();
						for (int ii=0;ii<dataArrays.FacilityNodes.get(order).childrenList.size();ii++)
						{
							for (int iv=0;iv<dataArrays.FacilityNodes.get(order).facilityStructure.size();iv++){
								if (!(((ArrayList) dataArrays.FacilityNodes.get(order).facilityStructure.get(iv)).get(1) 
										instanceof ArrayList)){
									struc.add((ArrayList) dataArrays.FacilityNodes.get(order).facilityStructure.get(iv));
								} else {
									ArrayList<ArrayList> subtruc =(ArrayList) dataArrays.FacilityNodes.get(order).facilityStructure.get(iv);
									ArrayList<ArrayList> realsub=(ArrayList)subtruc.get(1);
									struc.add(realsub);
								}
							}
							boolean truth=true;
				//add
							
							if (truth==true){
								displaylist.add(dataArrays.FacilityNodes.get(order).childrenList.get(ii));
								display.getChildren().add(dataArrays.FacilityNodes.get(order).childrenList.get(ii));
							}
							
						}
						System.out.println(displaylist.toString());
						System.out.println(struc);
						System.out.println(dataArrays.FacilityNodes.get(order).childrenList.get(0).facilityData);
					}
				});
				
				detailBox.getChildren().add(list);
			}
		});
		

		pane.setTop(topBox);
		pane.setLeft(display);
		
		setContent (pane);
		
	
}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	//need fix the data for this
	protected void formSort(ArrayList<Object> struc, ArrayList<Object> data,ArrayList<Object> facilitySortArray){
		for (int i=0;i<struc.size();i++){
			if (struc.size()>2){
				if (struc.get(i) instanceof ArrayList && struc.get(0) instanceof ArrayList){
					formSort((ArrayList<Object>)struc.get(i),(ArrayList<Object>)data.get(i),facilitySortArray);
				}else if (i==0){
					if (struc.get(2)=="oneOrMore"||struc.get(2)=="zeroOrMore"){
						ArrayList <Object> newData=new ArrayList<>();
						ArrayList<Object> newStruc=(ArrayList<Object>)struc.get(1);
						for (int ii=0;ii<newStruc.size();ii++){
							ArrayList<Object> element=new ArrayList<>();
							for (int iii=0;iii<data.size();iii++){
								ArrayList<Object> subData=(ArrayList<Object>) data.get(iii);
								ArrayList<Object>dataCol=(ArrayList<Object>)subData.get(0);
								element.add(dataCol.get(ii));
							}
							newData.add(element);
						}
						formSort(newStruc,newData,facilitySortArray);
					}else if (struc.get(1) instanceof ArrayList){
						ArrayList <Object> newData=new ArrayList<>();
						ArrayList<Object> newStruc=(ArrayList<Object>)struc.get(1);
						for (int ii=0;ii<newStruc.size();ii++){
							ArrayList<Object> element=new ArrayList<>();
							for (int iii=0;iii<data.size();iii++){
								ArrayList<Object> subData=(ArrayList<Object>) data.get(iii);
								ArrayList<Object>dataCol=(ArrayList<Object>)subData.get(0);
								element.add(dataCol.get(ii));
							}
							newData.add(element);
						}
						formSort(newStruc,newData,facilitySortArray);
						
					}else {
						ArrayList<Object> element=new ArrayList<Object>();
						element.add(struc);
						element.add(data);
						if (!(facilitySortArray.contains(element))){
							facilitySortArray.add(element);
						}	
					}
				}
			} else formSort((ArrayList<Object>)struc.get(i),(ArrayList<Object>)data.get(i),facilitySortArray);
		}
	}	
}
	

