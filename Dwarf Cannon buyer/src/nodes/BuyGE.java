package nodes;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;

import api.Node;
import m.Main;
import util.Glory;
import util.Ring;
import util.Vars;

/**
 * Created by T on 11/06/2017.
 */

public class BuyGE implements Node {
	private Main m;
	public BuyGE(Main m) {
		this.m = m;

	}

	public boolean interactGe() {
		if (!m.getGrandExchange().isOpen()) {
			if(!m.getGrandExchange().open()){
				findPathToGe();
			}
		}
		if (m.getGrandExchange().isOpen() && !m.getGrandExchange().isBuyOpen()) {
			m.slot = m.getGrandExchange().getFirstOpenSlot();
			m.getGrandExchange().openBuyScreen(m.slot);
		}
		if (m.getGrandExchange().isBuyOpen()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validate() {
		if (checkForGlory() && checkForRing()) {
			return false;
		}
		checkForGlory();
		checkForRing();
		return true;
	}

	@Override
	public int execute() {
		freeInv();
		if (!checkForGlory()) {
			buyGlory();
		}
		if (!checkForRing()) {
			buyRing();
		}
		return 350;
	}

	public void findPathToGe() {
		if (util.Dis.eQ(m.getLocalPlayer().getTile(), Vars.getGe(), Vars.getBooth(), 69)) {
			if (!m.getTabs().isOpen(Tab.EQUIPMENT)) {
				m.getTabs().open((Tab.EQUIPMENT));
				MethodProvider.sleepUntil(() -> m.getTabs().isOpen(Tab.EQUIPMENT), 2000);
			} else {
				if (m.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).interact("Grand Exchange")) {
					MethodProvider.sleep(650);
					m.removeRCharge();
				}
				MethodProvider.sleep(420);
			}
		}
		m.getWalking().walk(Vars.getBooth());
	}
	

	private void buyRing() {
		if (m.getInventory().contains(Ring.RING5.getId())) {

			if (!m.getGrandExchange().isOpen()) {
				m.getInventory().get(Ring.RING5.getId()).interact("Wear");
			}

		}
		if (interactGe() && !m.getGrandExchange().isReadyToCollect(m.slot)) {
			m.getGrandExchange().buyItem("Ring of wealth (5)", 1, Calculations.random(19000, 21000));
			MethodProvider.sleepUntil(() -> m.getGrandExchange().isReadyToCollect(m.slot), 2000);
		}
		if (m.getGrandExchange().isReadyToCollect(m.slot)) {
			m.getGrandExchange().collect();
			m.getGrandExchange().close();
			MethodProvider.sleepUntil(() -> m.getInventory().contains(Ring.RING5.getId()), 3000);
		}

	}

	private void freeInv() {
		if (m.getInventory().contains(Vars.getRingId())) {
			m.getBank().openClosest();
			MethodProvider.sleepUntil(() -> m.getBank().isOpen(), 1500);
			if (m.getBank().isOpen()) {
				m.getInventory().get(Vars.getRingId()).interact("Deposit-All");
				m.getBank().close();
			}
		}
		if (m.getInventory().contains(Vars.getGloryId())) {
			m.getBank().openClosest();
			MethodProvider.sleepUntil(() -> m.getBank().isOpen(), 1500);
			if (m.getBank().isOpen()) {
				m.getInventory().get(Vars.getGloryId()).interact("Deposit-All");
				m.getBank().close();
			}
		}
	}

	private void buyGlory() {
		if (m.getInventory().contains(Glory.GLORY6.getId())) {

			if (!m.getGrandExchange().isOpen()) {
				m.getInventory().get(Glory.GLORY6.getId()).interact("Wear");
			}
		}
		if (interactGe() && !m.getGrandExchange().isReadyToCollect(m.slot)) {
			m.getGrandExchange().buyItem("Amulet of glory(6)", 1, Calculations.random(19000, 21000));
			MethodProvider.sleepUntil(() -> m.getGrandExchange().isReadyToCollect(m.slot), 2000);
		}
		if (m.getGrandExchange().isOpen() && m.getGrandExchange().isReadyToCollect(m.slot)) {
			m.getGrandExchange().collect();
			m.getGrandExchange().close();
			MethodProvider.sleepUntil(() -> m.getInventory().contains(Glory.GLORY6.getId()), 3000);
		}

	}

	@Override
	public String status() {
		return "Buying amulet/ring";

	}

	private boolean checkForGlory() {
		int amuletId = m.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()) != null
				? m.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()).getID() : -1;
		if (amuletId == 1704) {
			m.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()).interact("Remove");

		}
		if (amuletId != -1) {
			for (Glory g : Glory.values()) {
				if (g.getId() == amuletId) {
					m.setGloryCharge(g.getCharges());
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkForRing() {
		int ringId = m.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()) != null
				? m.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).getID() : -1;
		if (ringId == Vars.getRingId()) {
			m.getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot()).interact("Remove");
			m.getBank().openClosest();
			MethodProvider.sleepUntil(() -> m.getBank().isOpen(), 1500);
			if (m.getBank().isOpen()) {
				m.getInventory().get(Vars.getRingId()).interact("Deposit-All");
				m.getBank().close();
			}
		}
		if (ringId != -1) {
			for (Ring r : Ring.values()) {
				if (r.getId() == ringId) {
					m.setRingCharge(r.getCharges());
					return true;
				}
			}
		}
		return false;
	}


}
