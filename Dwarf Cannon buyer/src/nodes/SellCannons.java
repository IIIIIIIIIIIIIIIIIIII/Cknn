package nodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import api.Node;
import m.Main;
import util.Vars;

/**
 * Created by T on 11/06/2017.
 */

public class SellCannons implements Node {
	private Main m;
	

	public SellCannons(Main m) {
		this.m = m;
	}

	@Override
	public boolean validate() {
		return true;

	}

	@Override
	public int execute() {
		if (invHasCannonParts()) {
			NPC geClerk = m.getNpcs().closest(Vars.getClerk());
			if (geClerk != null) {
				geClerk.interact("Sets");
				MethodProvider.sleepUntil(() -> m.getWidgets().getWidgetChild(451, 0).isVisible(), 2000);
			} else {
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
			if (setsOpen()) {
				makeSets();
			}
		}

		if (invHasCannonSets()) {
			if (!m.getGrandExchange().isOpen()) {
				m.getGrandExchange().open();
			}
			if (m.getGrandExchange().isOpen()) {
				m.getGrandExchange().sellItem("Dwarf Cannon Set", m.getInventory().count(12863), m.sellOffer + 10000);
				m.getGrandExchange().close();
				MethodProvider.sleepUntil(() -> m.getGrandExchange().isReadyToCollect(), 3000);
			}
		}

		return 860;
	}

	private void makeSets() {
		if (m.getWidgets().getWidgetChild(451, 2).getChild(0).interact("Pack")) {
			m.getGrandExchange().close();
			MethodProvider.sleepUntil(() -> !invHasCannonParts(), 1500);
		}

	}

	private boolean invHasCannonParts() {
		return m.getInventory().contains(6, 8, 10, 12);
	}

	private boolean invHasCannonSets() {
		return m.getInventory().contains(Vars.getCannonSet());
	}

	private boolean setsOpen() {
	        WidgetChild button = m.getWidgets().getWidgetChild(451, 2);
	        return button != null && button.isVisible();
	   
	}

	@Override
	public String status() {
		return "Selling and making sets";
	}

}
