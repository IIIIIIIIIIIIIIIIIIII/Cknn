package m;

import java.awt.Graphics;

import java.awt.Graphics2D;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;


import api.Node;
import nodes.BuyCannon;
import nodes.BuyGE;
import nodes.SellCannons;
import util.Vars;

/**
 * Created by T on 11/06/2017.
 */

@ScriptManifest(category = Category.MONEYMAKING, name = "Testo Cannon Buyer", author = "Testosterone", version = 1.0)
public class Main extends AbstractScript {

	private Node[] nodes;
	private String status;
	public int gloryCharge, ringCharge, cannonsBought, profit, sellOffer = 0;
	public int slot;

	@Override
	public void onStart() {
		nodes = new Node[] { new BuyGE(this), new BuyCannon(this), new SellCannons(this) };
		sellOffer = util.GeLookup.getAverageBuyOffer(12863);
	}

	@Override
	public int onLoop() {
		 if (getClient().isLoggedIn()) {
			 if(!getInventory().contains(Vars.getCoins()) || getInventory().get(Vars.getCoins()).getAmount()<750001){
				 log("Get more money bro");
				 getClient().getInstance().getScriptManager().stop();
			 }
             for (Node n : nodes) {
                 if (n.validate()) {
                     status = n.status();
                     n.execute();
                     break;
                 }
             }
         }
		return Calculations.random(650, 1650);
	}

	public void setGloryCharge(int c) {
		gloryCharge = c;
	}

	public void setRingCharge(int c) {
		ringCharge = c;
	}

	public void removeGCharge() {
		gloryCharge -= 1;
	}

	public void removeRCharge() {
		ringCharge -= 1;
	}

	@Override
	public void onPaint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.drawString("Status: " + status, 355, 280);
		g.drawString("Cannon sell price: " + util.Formater.format(sellOffer), 355, 295);
		g.drawString("GloryCharges : " + gloryCharge, 355, 340);
		g.drawString("RingCharge  : " + ringCharge, 355, 325);
		g.drawString("Will sell for  : " + util.Formater.format(sellOffer + 10000), 355, 310);
	}

}