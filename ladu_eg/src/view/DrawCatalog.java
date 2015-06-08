package view;

import java.util.Iterator;
import java.util.List;

import util.DBUtil;
import db.ItemType;

public class DrawCatalog {

	public ItemType selectedItemType;

	public ItemType getCatalog(int item_type_id) {
		DBUtil eventManager = new DBUtil();
		ItemType ItemTypeCatalog = null;
		ItemTypeCatalog = eventManager.getItemTypeById(item_type_id);
		return ItemTypeCatalog;
	}

	public List<ItemType> getSubCatalogs(int id) {
		DBUtil eventManager = new DBUtil();
		List<ItemType> ItemTypeSubCatalogs = null;
		ItemTypeSubCatalogs = eventManager.getItemTypeCatalogs(id);
		return ItemTypeSubCatalogs;
	}

	public String drawCatalog(ItemType itemType, int selected_catalog_id) {
		String catalog_out = "";
		String selected_indicator = "";
		if (selected_catalog_id == itemType.getItemType()) {
			this.selectedItemType = itemType;
			selected_indicator = "<b>[*]</b>";
		}
		ItemType subcatalog = null;
		for (int i = 0; i < (itemType.getLevel() - 1); i++) {
			catalog_out = catalog_out + "--";
		}
		catalog_out = catalog_out + selected_indicator + "<a href=\"?catalog="
				+ itemType.getItemType() + "\">" + itemType.getTypeName()
				+ "</a><br>\n";
		
		Iterator<ItemType> iter = itemType.getItemTypes().iterator();
		while (iter.hasNext()) {
			subcatalog = (ItemType) iter.next();
			catalog_out = catalog_out
					+ drawCatalog(subcatalog, selected_catalog_id);
		}
		return catalog_out;
	}
	
	public String DrawCatalogTree(int selected_catalog_id) {
		DBUtil eventManager = new DBUtil();
		List<ItemType> firstLevelCatalogs = eventManager.getAllFirstlevelCatalogs();
		String catalog_out = "";

		for (ItemType item : firstLevelCatalogs) {
			catalog_out = catalog_out
					+ drawCatalog(item, selected_catalog_id);
		}
		return catalog_out;

	}

}
