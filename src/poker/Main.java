package poker;
	
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.*;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import poker.TexasHoldem;
import poker.Card;
import poker.TexasHoldem.Sticks;
import poker.Player;


public class Main extends Application {
	List <poker.Card> deliveredCards = new ArrayList<>();
	List <poker.Card> cardsInTable = new ArrayList<>();
	poker.TexasHoldem game = new poker.TexasHoldem();
	Random rd = new Random();					
	int nofplayers = 9;
	List <SimpleDoubleProperty> stacks = new ArrayList<>();



	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane bp = new BorderPane();
			Pane deck = new Pane();
			deck.setPrefSize(1100,700);
			primaryStage.setTitle("Texas Holdem automatic Hands");
			
			
			

			
			

			Button nhand = new Button("New hand");
			List <Player> players = new ArrayList<>();

			Pane playerseats = new Pane();
			playerseats.setPrefSize(deck.getPrefWidth(), deck.getPrefHeight());
			playerseats.getChildren().add(deck);
			Pane [] plsits = new Pane[nofplayers];
			Pane [] deckcards = new Pane[5];

			nhand.setOnAction(e->newHand(deck,plsits,deckcards));
		
			
			bp.setCenter(playerseats); bp.setBottom(nhand);
			Scene scene = new Scene(bp);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			bp.getStyleClass().add("pane");
			
			for(int i =0; i<plsits.length; i++) {
				Pane np = new Pane();
				np.setPrefSize(100, 80);
				np.setOpacity(0.5);
				np.setStyle("-fx-background-color: brown; -fx-boder-radius:30;");
				playerseats.getChildren().add(np);
				plsits[i]=np;
			}
			for(int i =0; i<deckcards.length; i++) {
				Pane np = new Pane();
				np.setPrefSize(40, 70);
				np.setOpacity(0.5);
				np.setStyle("-fx-background-color: darkgreen; -fx-boder:2px;-fx-border-color:white;");
				playerseats.getChildren().add(np);
				deckcards[i]=np;
			}
			AtomicInteger mv = new AtomicInteger(0);
			scene.addEventHandler(MouseEvent.MOUSE_MOVED, e->{
				//deckcards[mv.get()].setLayoutX(e.getSceneX());
				//deckcards[mv.get()].setLayoutY(e.getSceneY());

			});
			scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
				//System.out.println(" deckcards["+mv.get()+"].setLayoutX("+deckcards[mv.get()].getLayoutX()+");");
				//System.out.println(" deckcards["+mv.get()+"].setLayoutY("+deckcards[mv.getAndIncrement()].getLayoutY()+");");
				//if(mv.get()>=deckcards.length)mv.set(0);

			});
			primaryStage.setScene(scene);
			primaryStage.show();
			
			 deckcards[0].setLayoutX(444.0);
			 deckcards[0].setLayoutY(261.6);
			 deckcards[1].setLayoutX(495.2);
			 deckcards[1].setLayoutY(262.4);
			 deckcards[2].setLayoutX(548.0);
			 deckcards[2].setLayoutY(262.4);
			 deckcards[3].setLayoutX(601.6);
			 deckcards[3].setLayoutY(263.2);
			 deckcards[4].setLayoutX(652.0);
			 deckcards[4].setLayoutY(263.2);
			 plsits[0].setLayoutX(216.8);
			 plsits[0].setLayoutY(172.0);
			 plsits[1].setLayoutX(144.8);
			 plsits[1].setLayoutY(328.8);
			 plsits[2].setLayoutX(314.4);
			 plsits[2].setLayoutY(448.0);
			 plsits[3].setLayoutX(546.4);
			 plsits[3].setLayoutY(476.0);
			 plsits[4].setLayoutX(806.4);
			 plsits[4].setLayoutY(424.8);
			 plsits[5].setLayoutX(921.6);
			 plsits[5].setLayoutY(261.6);
			 plsits[6].setLayoutX(770.4);
			 plsits[6].setLayoutY(120.8);
			 plsits[7].setLayoutX(579.2);
			 plsits[7].setLayoutY(48.8);
			 plsits[8].setLayoutX(377.6);
			 plsits[8].setLayoutY(71.2);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	double pot=0.0;
	private void newHand(Pane deck,Pane [] plsits, Pane [] deckcards){
		if(deck.getChildren().size()>5)deck.getChildren().clear();
		for(Pane p:plsits)if(p.getChildren().size()>0)p.getChildren().clear();
		for(Pane p:deckcards)if(p.getChildren().size()>0)p.getChildren().clear();

		Sticks [] sticks = {Sticks.CLUBS,Sticks.DIAMONDS, Sticks.HEARTS,Sticks.SPADES};
			String [] cardcharacters = game.cards;
			
			for(int i =0; i<cardcharacters.length; i++) {
				for(int j = 0; j<sticks.length; j++) {
					Card ncard = new Card(cardcharacters[i], j);
					cardsInTable.add(ncard);
				}
			}
			List <Card> shuffledCards = shuffle(cardsInTable);
			Logger log= Logger.getLogger("");
			log.info("Shuffling cards");
			//Cards shuffled
			
			List <Player> players = new ArrayList<>();
			
			if(shuffledCards.size()!=cardsInTable.size()) {
				Alert al = new Alert(AlertType.WARNING);
				al.setContentText("Some cards are not in hand, " + Math.abs(shuffledCards.size()-cardsInTable.size())+ " are missing. ");
				al.setHeaderText("WARNING");
				al.showAndWait();
			}
			double startStack = 30000.0;

			for(int i =0;i<nofplayers;i++) {
				
				//New Cards to each player
				int si =2*i;
				Card c1 = shuffledCards.get(si);
				Card c2 = shuffledCards.get(si +1);
				Player npl = new Player(c1, c2);
				players.add(npl);
				deliveredCards.add(c1);deliveredCards.add(c2);
				players.add(npl);
				Pane np = new Pane();
				np.setPrefSize(100, 100);
				deck.getChildren().add(np);
				SimpleDoubleProperty nstack = new SimpleDoubleProperty(startStack);
				if(stacks.size()<nofplayers+1)stacks.add(nstack);

				np.setStyle("-fx-background-color: red; -fx-border-radius: 10 px;");
				np.setBackground(background(new Image("file:files/imgs/players/pl"+rd.nextInt(0,getNumberOfImgsInPath())+".png")));
				Label stackLabel = new Label(stacks.get(i).get()+" €");
				stackLabel.setStyle("-fx-font-size: 16 px; -fx-fx-text-fil: darkgreen; -fx-font-family: 'Times New Roman';");
				nstack.addListener((o,ov,n)->{
					stackLabel.setText(n+" €");
				});
				stackLabel.setText(stacks.get(i).get()+" €");
				
				np.setOpacity(0.8);
				players.get(i).setCards(c1, c2);
				Card [] playercards = players.get(i).getCards();
		
				
				
				Pane card1 = cardPane(c1.character(),c1.stick()+"");
				Pane card2 = cardPane(c2.character(),c2.stick()+"");

				HBox cardsBox = new HBox(3, card1,card2);cardsBox.setLayoutX(20);cardsBox.setLayoutY(-20);
				Player pl = new Player(playercards[0],playercards[1]);
				pl.cards =cardsBox;
				players.add(pl);
				if(stacks.size()<nofplayers+1)players.add(pl);
				
				cardsBox.setLayoutX(np.getLayoutX()); cardsBox.setLayoutY(np.getLayoutY()+50);
				stackLabel.setLayoutX(np.getLayoutX()+10); stackLabel.setLayoutY(np.getLayoutY()+150);
				
				plsits[i].getChildren().addAll(cardsBox,stackLabel);
				
			}
			
			Pane [] cardsIcons = new Pane  [nofplayers];
			boolean [] playerHasPair = new boolean [nofplayers+1];
			boolean [] playerHasSameStickPf = new boolean [nofplayers+1];
			boolean [] playerIsAtHand = new boolean [nofplayers+1];

			AtomicReference<Double> curbet = new AtomicReference(0.0);
			
			for (Player p : players)try{p.cards.setOpacity(1);}catch(Exception ex) {}

			

			for(int i =0;i<nofplayers;i++) {
				playerHasPair[i]=false;
				playerHasSameStickPf[i] =false;
				
				Card [] playercards = players.get(i).getCards();
				if(playercards[0].character()==playercards[1].character()) playerHasPair[i]=true;
				if(playercards[0].stick()==playercards[1].stick()) playerHasSameStickPf[i]=true;
				//Label nl = new Label(playercards[0].character()+playercards[0].stick() + " "+playercards[1].character()+playercards[1].stick());
				
				//nl.setLayoutX(lx);nl.setLayoutY(ly);
				if(playerHasPair[i]&&curbet.get()<400.0) {
					double ammountBet = 400.0;
					pot+=curbet.get();

					curbet.set(ammountBet);
					double cstack = stacks.get(i).get();
					stacks.get(i).set(cstack-ammountBet);
					playerIsAtHand[i] =true;
					Label nbl = new Label("Raise to " +ammountBet+"$");
					nbl.setStyle("-fx-font-size: 16 px; -fx-fx-text-fil: Cyan; -fx-font-family: 'Consolas';");
					nbl.setLayoutX(plsits[i].getLayoutX()+600);					nbl.setLayoutY(plsits[i].getLayoutY()+20);
					deck.getChildren().add(nbl);
					animatedBet(nbl);
					PauseTransition ptr= new PauseTransition(Duration.millis(5000));
					ptr.play();
					ptr.setOnFinished(e->{
					nbl.setOpacity(0);
					deck.getChildren().remove(nbl);});
				}
				if(curbet.get()>=400.0&&!playerHasPair[i] && !playerHasSameStickPf[i]) {
					Player pl = players.get(i);
					double cstack = stacks.get(i).get();
					stacks.get(i).set(cstack);
					Label nbl = new Label("Player Folded to bet");
					nbl.setStyle("-fx-font-size: 16 px; -fx-fx-text-fil: Cyan; -fx-font-family: 'Consolas';");
					nbl.setLayoutX(plsits[i].getLayoutX()+600);					nbl.setLayoutY(plsits[i].getLayoutY()+20);
					deck.getChildren().add(nbl);
					playerIsAtHand[i] =false;

					animatedBet(nbl);
					try{pl.cards.setOpacity(0.3);}catch(Exception ex) {}
					PauseTransition ptr= new PauseTransition(Duration.millis(5000));
					ptr.play();
					ptr.setOnFinished(e->{
					nbl.setOpacity(0);
					deck.getChildren().remove(nbl);});

				}
				if(playerHasSameStickPf[i]&&curbet.get()<400.0) {
					double ammountBet = 400.0;
					curbet.set(ammountBet);
					playerIsAtHand[i] =true;
					pot+=curbet.get();

					double cstack = stacks.get(i).get();
					stacks.get(i).set(cstack-ammountBet);
					Label nbl = new Label("Raise to " +ammountBet+"$");
					nbl.setStyle("-fx-font-size: 16 px; -fx-text-fil: Cyan; -fx-font-family: 'Consolas';");
					nbl.setLayoutX(plsits[i].getLayoutX()+600);					nbl.setLayoutY(plsits[i].getLayoutY()+20);
					deck.getChildren().add(nbl);
					animatedBet(nbl);

					PauseTransition ptr= new PauseTransition(Duration.millis(5000));
					ptr.play();
					ptr.setOnFinished(e->{
					nbl.setOpacity(0);
					deck.getChildren().remove(nbl);});

				}
				if(playerHasPair[i]&&(playercards[1].character().replaceAll("[^A-z]","").length()>0&&playercards[1].character().equals("A")) && curbet.get()>10.0) {
					double obet =curbet.get();
					double ammountBet = obet*2;
					curbet.set(ammountBet);
					double cstack = stacks.get(i).get();
					stacks.get(i).set(cstack-ammountBet);
					playerIsAtHand[i] =true;
					pot+=curbet.get();

					Label nbl = new Label("Raise to " +ammountBet+"$");
					nbl.setStyle("-fx-font-size: 16 px; -fx-text-fill: Cyan; -fx-font-family: 'Consolas';");
					nbl.setLayoutX(plsits[i].getLayoutX()+600);					nbl.setLayoutY(plsits[i].getLayoutY()+20);
					deck.getChildren().add(nbl);
					animatedBet(nbl);

					PauseTransition ptr= new PauseTransition(Duration.millis(5000));
					ptr.play();
					ptr.setOnFinished(e->{
					nbl.setOpacity(0);
					deck.getChildren().remove(nbl);});

				}
				

			}
			PauseTransition ptr = new PauseTransition(Duration.millis(4000));
			ptr.play();
			
			Timeline flop = new Timeline(new KeyFrame(Duration.millis(10),ev->{
				//Flop
				log.info("Flop");

				curbet.set(0.0);
				for(int i =0;i<3;i++) {
					
					Card c1 = shuffledCards.get(deliveredCards.size()-1);
					Pane np = cardPane(c1.character(),c1.stick()+"");

					
					deliveredCards.add(c1);
					Label nl = new Label();
					
					deckcards[i].getChildren().addAll(np);

				}
				List<String> lastNumbers = List.of(deliveredCards.get(deliveredCards.size()-1).stick,deliveredCards.get(deliveredCards.size()-2).stick,
					deliveredCards.get(deliveredCards.size()-3).stick);
				List<Integer> lastSticks = List.of(deliveredCards.get(deliveredCards.size()-1).stickN,deliveredCards.get(deliveredCards.size()-2).stickN,
						deliveredCards.get(deliveredCards.size()-3).stickN);
				for(int i =0;i<nofplayers;i++) {
					boolean hasPair =false;
					boolean hasTrips= false;
					boolean hasStraight =false;
					boolean hasFullHouse = false;
					boolean hasFlush = false;
					boolean hasQuads =false;
					if(playerIsAtHand[i]) {
						Card c1=players.get(i).card1;
						Card c2=players.get(i).card1;
						int cs1 = c1.stickN;
						int cs2 =c2.stickN;
						String cnb1 = c1.stick;
						String cnb2 = c2.stick;
						int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
						int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
						
						if(pairedcards == 1) hasPair =true;
						if(pairedcards == 2) hasTrips =true;
						if(pairedcards == 3) hasQuads =true;
						if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
						if(samesticks == 4) hasFlush =true;
						hasStraight = getStraight(lastNumbers,cnb1,cnb2);
						if(hasPair||hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()<10) {
							curbet.set(200.0);
							double cstack = stacks.get(i).get();
							stacks.get(i).set(cstack+curbet.get());
							pot+=curbet.get();

						}
						else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>10) {
							double oldb = curbet.get();
							curbet.set(oldb*2);
							pot+=curbet.get();

							double cstack = stacks.get(i).get();
							stacks.get(i).set(cstack-curbet.get());
							String hand = (!hasPair && !hasQuads&&!hasTrips&&!hasFlush&& hasStraight)?"straight":
								( !hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair)?"pair":
									( !hasQuads&&!hasPair&&!hasFlush&&!hasStraight&& hasTrips)?"trips":
										( !hasPair&&!hasTrips&&!hasFlush&&!hasStraight&& hasQuads)?"quads":"flush";
							String raises = (oldb<curbet.get())?"raises to "+ curbet.get(): "checks/calls";
							System.out.println("Player "+ i +" "+raises+" with "+hand+".");
						}
					
					}
				}
				//calls,folds
				for(int i =0;i<nofplayers;i++) {
					boolean hasPair =false;
					boolean hasTrips= false;
					boolean hasStraight =false;
					boolean hasFullHouse = false;
					boolean hasFlush = false;
					boolean hasQuads =false;
					if(playerIsAtHand[i]) {
						Card c1=players.get(i).card1;
						Card c2=players.get(i).card1;
						int cs1 = c1.stickN;
						int cs2 =c2.stickN;
						String cnb1 = c1.stick;
						String cnb2 = c2.stick;
						int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
						int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
						
						if(pairedcards == 1) hasPair =true;
						if(pairedcards == 2) hasTrips =true;
						if(pairedcards == 3) hasQuads =true;
						if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
						if(samesticks == 4) hasFlush =true;
						hasStraight = getStraight(lastNumbers,cnb1,cnb2);
						//fold
						playerIsAtHand[i] =true;
						if(!hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair&& curbet.get()>200.0) {
							playerIsAtHand[i] =false;players.get(i).cards.setOpacity(0.5);
						}
						//call
						else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>200) {
							double cstack = stacks.get(i).get();
							stacks.get(i).set(cstack-curbet.get());
							pot+=curbet.get();

						}
					
					}
				}
				
			}));
			ptr.setOnFinished(e->{
				
				flop.play();
			});
			Timeline turn = new Timeline(new KeyFrame(Duration.millis(4000),ev->{
				curbet.set((double) 0);

				//Turn
				Card cd = shuffledCards.get(deliveredCards.size()-1);
				Pane np = cardPane(cd.character(),""+cd.stick());
				
				deliveredCards.add(cd);
				Label nl = new Label(cd.character()+cd.stick());
				
				deckcards[3].getChildren().addAll(np);		
				List<String> lastNumbers = List.of(deliveredCards.get(deliveredCards.size()-1).stick,deliveredCards.get(deliveredCards.size()-2).stick,
						deliveredCards.get(deliveredCards.size()-3).stick,deliveredCards.get(deliveredCards.size()-4).stick);
					List<Integer> lastSticks = List.of(deliveredCards.get(deliveredCards.size()-1).stickN,deliveredCards.get(deliveredCards.size()-2).stickN,
							deliveredCards.get(deliveredCards.size()-3).stickN,deliveredCards.get(deliveredCards.size()-4).stickN);
					for(int i =0;i<nofplayers;i++) {
						boolean hasPair =false;
						boolean hasTrips= false;
						boolean hasStraight =false;
						boolean hasFullHouse = false;
						boolean hasFlush = false;
						boolean hasQuads =false;
						if(playerIsAtHand[i]) {
							Card cd1=players.get(i).card1;
							Card c2=players.get(i).card1;
							int cs1 = cd1.stickN;
							int cs2 =c2.stickN;
							String cnb1 = cd1.stick;
							String cnb2 = c2.stick;
							int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
							int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
							
							if(pairedcards == 1) hasPair =true;
							if(pairedcards == 2) hasTrips =true;
							if(pairedcards == 3) hasQuads =true;
							if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
							if(samesticks == 4) hasFlush =true;
							hasStraight = getStraight(lastNumbers,cnb1,cnb2);
							if(hasPair||hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()<10) {
								curbet.set(200.0);
								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack+curbet.get());
								pot+=curbet.get();

							}
							else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>10) {
								double oldb = curbet.get();
								curbet.set(oldb*2);
								pot+=curbet.get();

								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack-curbet.get());
								String hand = (!hasPair && !hasQuads&&!hasTrips&&!hasFlush&& hasStraight)?"straight":
									( !hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair)?"pair":
										( !hasQuads&&!hasPair&&!hasFlush&&!hasStraight&& hasTrips)?"trips":
											( !hasPair&&!hasTrips&&!hasFlush&&!hasStraight&& hasQuads)?"quads":"flush";
								String raises = (oldb<curbet.get())?"raises to "+ curbet.get(): "checks/calls";
								System.out.println("Player "+ i +" "+raises+" with "+hand+".");
							}
						
						}
					}
					//calls,folds
					for(int i =0;i<nofplayers;i++) {
						boolean hasPair =false;
						boolean hasTrips= false;
						boolean hasStraight =false;
						boolean hasFullHouse = false;
						boolean hasFlush = false;
						boolean hasQuads =false;
						if(playerIsAtHand[i]) {
							Card cd1=players.get(i).card1;
							Card c2=players.get(i).card1;
							int cs1 = cd1.stickN;
							int cs2 =c2.stickN;
							String cnb1 = cd1.stick;
							String cnb2 = c2.stick;
							int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
							int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
							
							if(pairedcards == 1) hasPair =true;
							if(pairedcards == 2) hasTrips =true;
							if(pairedcards == 3) hasQuads =true;
							if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
							if(samesticks == 4) hasFlush =true;
							hasStraight = getStraight(lastNumbers,cnb1,cnb2);
							//fold
							playerIsAtHand[i] =true;
							if(!hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair&& curbet.get()>200.0) {
								playerIsAtHand[i] =false;players.get(i).cards.setOpacity(0.5);
							}
							//call
							else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>200) {
								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack-curbet.get());
								pot+=curbet.get();

							}
						
						}
					}
			}));
			flop.setOnFinished(ev->{
				turn.play();
			});
			Timeline river = new Timeline(new KeyFrame(Duration.millis(4000),ev->{
				//River
				Card cd = shuffledCards.get(deliveredCards.size()-1);
				Pane np = cardPane(cd.character(),""+cd.stick());
				
				deliveredCards.add(cd);
				
				deckcards[4].getChildren().addAll(np);		
				List<String> lastNumbers = List.of(deliveredCards.get(deliveredCards.size()-1).stick,deliveredCards.get(deliveredCards.size()-2).stick,
						deliveredCards.get(deliveredCards.size()-3).stick,deliveredCards.get(deliveredCards.size()-4).stick,deliveredCards.get(deliveredCards.size()-5).stick);
					List<Integer> lastSticks = List.of(deliveredCards.get(deliveredCards.size()-1).stickN,deliveredCards.get(deliveredCards.size()-2).stickN,
							deliveredCards.get(deliveredCards.size()-3).stickN,deliveredCards.get(deliveredCards.size()-4).stickN,deliveredCards.get(deliveredCards.size()-5).stickN);
					for(int i =0;i<nofplayers;i++) {
						boolean hasPair =false;
						boolean hasTrips= false;
						boolean hasStraight =false;
						boolean hasFullHouse = false;
						boolean hasFlush = false;
						boolean hasQuads =false;
						if(playerIsAtHand[i]) {
							Card c1=players.get(i).card1;
							Card c2=players.get(i).card1;
							int cs1 = c1.stickN;
							int cs2 =c2.stickN;
							String cnb1 = c1.stick;
							String cnb2 = c2.stick;
							int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
							int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
							
							if(pairedcards == 1) hasPair =true;
							if(pairedcards == 2) hasTrips =true;
							if(pairedcards == 3) hasQuads =true;
							if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
							if(samesticks == 4) hasFlush =true;
							hasStraight = getStraight(lastNumbers,cnb1,cnb2);
							if(hasPair||hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()<10) {
								curbet.set(200.0);
								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack-curbet.get());
								pot+=curbet.get();

							}
							else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>10) {
								double oldb = curbet.get();
								curbet.set(oldb*2);
								pot+=curbet.get();

								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack-curbet.get());
								String hand = (!hasPair && !hasQuads&&!hasTrips&&!hasFlush&& hasStraight)?"straight":
									( !hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair)?"pair":
										( !hasQuads&&!hasPair&&!hasFlush&&!hasStraight&& hasTrips)?"trips":
											( !hasPair&&!hasTrips&&!hasFlush&&!hasStraight&& hasQuads)?"quads":"flush";
								String raises = (oldb<curbet.get())?"raises to "+ curbet.get(): "checks/calls";
								System.out.println("Player "+ i +" "+raises+" with "+hand+".");
							}
						
						}
					}
					//calls,folds
					for(int i =0;i<nofplayers;i++) {
						boolean hasPair =false;
						boolean hasTrips= false;
						boolean hasStraight =false;
						boolean hasFullHouse = false;
						boolean hasFlush = false;
						boolean hasQuads =false;
						if(playerIsAtHand[i]) {
							Card c1=players.get(i).card1;
							Card c2=players.get(i).card1;
							int cs1 = c1.stickN;
							int cs2 =c2.stickN;
							String cnb1 = c1.stick;
							String cnb2 = c2.stick;
							int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
							int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
							
							if(pairedcards == 1) hasPair =true;
							if(pairedcards == 2) hasTrips =true;
							if(pairedcards == 3) hasQuads =true;
							if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
							if(samesticks == 4) hasFlush =true;
							hasStraight = getStraight(lastNumbers,cnb1,cnb2);
							//fold
							playerIsAtHand[i] =true;
							if(!hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair&& curbet.get()>200.0) {
								playerIsAtHand[i] =false;
								players.get(i).cards.setOpacity(0.5);
							}
							//call
							else if(hasTrips||hasStraight||hasFullHouse||hasFlush||hasQuads&& curbet.get()>200) {
								double cstack = stacks.get(i).get();
								stacks.get(i).set(cstack-curbet.get());
								pot+=curbet.get();

							}
						
						}
					}
					//winner takes the pot
					String res = "";
					for(int i =0;i<nofplayers;i++) {
						boolean hasPair =false;
						boolean hasTrips= false;
						boolean hasStraight =false;
						boolean hasFullHouse = false;
						boolean hasFlush = false;
						boolean hasQuads =false;
						if(playerIsAtHand[i]) {
						Card c1=players.get(i).card1;
						Card c2=players.get(i).card1;
						int cs1 = c1.stickN;
						int cs2 =c2.stickN;
						String cnb1 = c1.stick;
						String cnb2 = c2.stick;
						int pairedcards = numberOfSameCards(lastNumbers,cnb1,cnb2);
						int samesticks = numberOfEqualSticks(lastSticks,cs1,cs2);
							
						if(pairedcards == 1) hasPair =true;
						if(pairedcards == 2) hasTrips =true;
						if(pairedcards == 3) hasQuads =true;
						if(pairedcards == 4 && cs1!=cs2) hasFullHouse =true;
						if(samesticks == 4) hasFlush =true;
						hasStraight = getStraight(lastNumbers,cnb1,cnb2);
						int phand = nofPlayersAtHand(playerIsAtHand); 
						

								String hand = (!hasPair && !hasQuads&&!hasTrips&&!hasFlush&& hasStraight)?"straight":
									( !hasQuads&&!hasTrips&&!hasFlush&&!hasStraight&& hasPair)?"pair":
										( !hasQuads&&!hasPair&&!hasFlush&&!hasStraight&& hasTrips)?"trips":
											( !hasPair&&!hasTrips&&!hasFlush&&!hasStraight&& hasQuads)?"quads":"flush";
								res+=hand+";";
								boolean doGet = (hand.contains("quads"))?true :(hand.contains("trips"))?res.contains("quads")&& !res.contains("flush")&& !res.contains("straight")
												:(hand.contains("trips"))?!res.contains("quads")&& !res.contains("flush")&& !res.contains("straight")
														:(hand.contains("pair"))?!res.contains("quads")&& !res.contains("trips")&&!res.contains("flush")&& !res.contains("straight")
																:(hand.contains("flush"))?!res.contains("quads")
							
																		:(hand.contains("straight"))?!res.contains("quads")&& !res.contains("flush"):false;;
							if(!doGet)playerIsAtHand[i]=false;
							if(doGet) {
								double finalpot = pot/phand;
								double cstack = stacks.get(i).get();
									stacks.get(i).set(cstack+finalpot);
									System.out.println("Player "+ i +" wins "+finalpot+" with "+hand+".");

							}
								
						
						}
					}
			}));
			turn.setOnFinished(ev->{
				river.play();
			});
	}
	int nofPlayersAtHand(boolean[] bls) {
		int count =0;
		for(boolean s: bls) {
			if(s==true)count+=1;
		}
		return count;
	}
	boolean getStraight(List<String> cards, String c1, String c2) {
		boolean has =false;
		List <String> nblist = List.of("A","2","3","4","5","6","7","8","9","T","J","Q","K","A");
		int count =0;
		int nbsid1 = nblist.indexOf(c1);
		int nbsid2 = nblist.indexOf(c2);
		for(String s: cards) {
			
			int nbsidc = nblist.indexOf(s);
			if(Math.abs(nbsid1-nbsidc)==1) {
				count+=1;
			}
			if(Math.abs(nbsid2-nbsidc)==1) {
				count+=1;
			}
		}
		if(Math.abs(nbsid2-nbsid1)==1) {
			count+=1;
		}
		if (count==5) has =true;
		return has;
	}
	int numberOfSameCards(List<String> cards, String c1, String c2) {
		int count =0;
		for(String s: cards) {
			if(c1.equals(s)||c2.equals(s)) {
				count+=1;
			}
		}
		if(c1==c2)count+=1;
		return count;
	}
	int numberOfEqualSticks(List<Integer> cards, int c1, int c2) {
		int count =0;
		for(int s: cards) {
			if(c1==s||c2==s) {
				count+=1;
			}
		}
		if(c1==c2)count+=1;
		return count;
	}
	private Pane cardPane(String string, String string2) {
		Pane cp = new Pane();
		cp.setPrefSize(50, 100);
		cp.setStyle("-fx-background-color:white;");
		String string2safe = string2.replace("1", "♦").replace("2", "♣").replace("3", "♥").replace("0", "♠");
		Label card = new Label(string+string2safe);
		card.setStyle((string2safe.contains("♠")||string2safe.contains("♣"))?"-fx-text-fill:black; -fx-font-size: 14px": "-fx-text-fill:red; -fx-font-size: 14px");
		cp.getChildren(). add(card);
		return cp;
	}
	private int getNumberOfImgsInPath() {
		List<Path> files = null;
		try {
			 files = Files.list(new File("files\\imgs\\players").toPath()).toList();
			return files.size()-1;

		}catch(IOException ex) {
			
		}
		return 10;
		}
	
	List <Card> shuffleForFirstTime() {
		List <Card> cardsInTable = new ArrayList<>();
		Sticks [] sticks = {Sticks.CLUBS,Sticks.DIAMONDS, Sticks.HEARTS,Sticks.SPADES};
		String [] cardcharacters = game.cards;
		
		for(int i =0; i<cardcharacters.length; i++) {
			for(int j = 0; j<sticks.length; j++) {
				Card ncard = new Card(cardcharacters[i], j);
				cardsInTable.add(ncard);
			}
		}
		return cardsInTable;
	}
	List<Card> shuffle(List<Card> cardsInTable){
		List <poker.Card> shuffledCards = new ArrayList<>();
		List<Integer> deliveredCards = new ArrayList<>();
		Random rd = new Random();
		for(int i =0;i<cardsInTable.size();i++) {
			int np = rd.nextInt(cardsInTable.size());
			if(!shuffledCards.contains(cardsInTable.get(np))) {shuffledCards.add(cardsInTable.get(np));
			if(!deliveredCards.contains(np))deliveredCards.add(np);}
		}
		for(int i =0; i<100;i++)
		if(deliveredCards.size()!=cardsInTable.size()) {
			addRemainingCards(cardsInTable, shuffledCards, deliveredCards);
		}
		
		return shuffledCards;
	}
	void addRemainingCards(List<Card> cardsInTable,List <poker.Card> shuffledCards, List<Integer> deliveredCards) {
		Random rd = new Random();
		for(int i =0;i<cardsInTable.size();i++) {
			int np = rd.nextInt(cardsInTable.size());
			if(!shuffledCards.contains(cardsInTable.get(np))) {shuffledCards.add(cardsInTable.get(np));
			if(!deliveredCards.contains(np))deliveredCards.add(np);}
		}

	}
	public static void main(String[] args) {
		launch(args);
	}
	void animatedBet(Label p) {
		Random r = new Random();
		Timeline tm = new Timeline(new KeyFrame(Duration.millis(10), e->{
			p.setLayoutX(p.getLayoutX()+r.nextDouble(0,2));
			p.setLayoutY(p.getLayoutY()+r.nextDouble(0,2));

		}));
		tm.setCycleCount(200);
		tm.play();
	}
	Background background(Image img) {
		BackgroundSize size = new BackgroundSize(1, 1, true, true, false, false);
		Background bck = new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
		return bck;
	}
}
