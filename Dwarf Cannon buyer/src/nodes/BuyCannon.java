package nodes;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import api.Node;
import m.Main;
import util.Vars;

public class BuyCannon implements Node {
	private Main m;

	public BuyCannon(Main m) {
		this.m = m;
	}

	@Override
	public boolean validate() {
		if (m.getInventory().emptySlotCount() > 3 && !m.getInventory().contains(Vars.getCannonSet())) {
			return true;
		}
		return false;
	}

	@Override
	public int execute() {
		m.getInventory().dropAllExcept(Vars.getCoins(), 6, 8, 10, 12);
		MethodProvider.sleepUntil(() -> m.getInventory().onlyContains(Vars.getCoins(), 6, 8, 10, 12), 3000);
		NPC seller = m.getNpcs().closest("Nulodion");
		if (seller != null) {
			if (m.getMap().canReach(seller.getTile())) {
				if (!m.getDialogues().inDialogue() && m.getInventory().onlyContains(Vars.getCoins(), 6, 8, 10, 12)) {
					seller.interact("Talk-to");
				}
			} else {
				GameObject door = m.getGameObjects().closest(p -> p.getID() == 3);
				if (door != null) {
					door.interact("Open");
				}
			}
		} else {
			if (util.Dis.eQ(m.getLocalPlayer().getTile(), Vars.getGlory(), Vars.getDwarf(), 65)) {
				if (!m.getTabs().isOpen(Tab.EQUIPMENT)) {
					m.getTabs().open((Tab.EQUIPMENT));
					MethodProvider.sleepUntil(() -> m.getTabs().isOpen(Tab.EQUIPMENT), 2000);
				} else {
					if (m.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot()).interact("Edgeville")) {
						MethodProvider.sleep(300);
						m.removeGCharge();
					}
					MethodProvider.sleep(420);
				}
			}
			m.getWalking().walk(Vars.getDwarf());
			MethodProvider.sleepUntil(() -> !m.getLocalPlayer().isMoving(), 3000);
		}

		if (m.getDialogues().inDialogue() && m.getDialogues().canContinue()) {
			m.getDialogues().continueDialogue();
		}
		if (m.getDialogues().inDialogue() && m.getDialogues().getOptions() != null) {
			m.getDialogues().chooseOption(1);
		}

		return 400;
	}

	@Override
	public String status() {
		return "Buying cannon and shit";
	}

}
