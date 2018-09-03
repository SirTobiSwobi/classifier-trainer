package org.SirTobiSwobi.c3.classifiertrainer.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class RelationshipManagerTest {

	@Test
	public void test() {
		/**
		 * Using Mesh categories and their relationships as real world relationship example
		 */
		CategoryManager catMan = new CategoryManager();
		RelationshipManager relMan = new RelationshipManager(catMan);
		
		Category category = new Category(0,"Diseases","");
		catMan.setCategory(category);
		category = new Category(4,"Neoplasms","");
		catMan.setCategory(category);
		category = new Category(557,"Neoplasms by Histologic Type","");
		catMan.setCategory(category);
		category = new Category(665,"Nevi and Melanomas","");
		catMan.setCategory(category);
		category = new Category(510,"Melanoma","");
		catMan.setCategory(category);
		category = new Category(385,"Hutchinson's Melanotic Freckle","");
		catMan.setCategory(category);
		category = new Category(515,"Melanoma, Amelanotic","");
		catMan.setCategory(category);
		category = new Category(525,"Melanoma, Experimental","");
		catMan.setCategory(category);
		category = new Category(2,"Virus Diseases","");
		catMan.setCategory(category);
		category = new Category(256,"DNA Virus Infections","");
		catMan.setCategory(category);
		category = new Category(466,"Herpesviridae Infections","");
		catMan.setCategory(category);
		category = new Category(382,"Herpes Simplex","");
		catMan.setCategory(category);
		category = new Category(465,"Keratitis, Herpetic","");
		catMan.setCategory(category);
		category = new Category(450,"Keratitis, Dendritic","");
		catMan.setCategory(category);
		
		relMan.addRelatonshipWithoutId(0, 4, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(4, 557, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(557, 665, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(665, 510, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 385, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 515, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(510, 525, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(0, 2, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(2, 256, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(256, 466, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(466, 382, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(382, 465, RelationshipType.Sub);
		relMan.addRelatonshipWithoutId(465, 450, RelationshipType.Sub);
		
		assertTrue("Diseases (0) has 2 subs ",relMan.getAllRelationshipsFrom(0).length==2);
		assertTrue("Melanoma (510) has 3 subs ",relMan.getAllRelationshipsFrom(510).length==3);
		assertTrue("Melanoma (510) has 1 from ",relMan.getAllRelationshipsTo(510).length==1);
		assertTrue("Melanoma (510) predecessor is Nevi and Melanomas (665)", relMan.getAllRelationshipsTo(510)[0].getFrom().getId()==665);
		long predecessorId = relMan.getAllRelationshipsTo(510)[0].getFrom().getId();
		assertTrue("Predecessor Id should be 665 ", predecessorId==665);
		assertTrue("Melanoma (510) predecessor Nevi and Melanomas has the right label", catMan.getByAddress(predecessorId).getLabel().equals("Nevi and Melanomas"));
		
		long deleteId = relMan.getAllRelationshipsTo(450)[0].getId();
		relMan.deleteRelationship(deleteId);
		assertTrue("There is no relationship between 465 and 450",relMan.getAllRelationshipsTo(450).length==0&&relMan.getAllRelationshipsFrom(465).length==0);
		assertTrue("The deleted Relationship is gone",relMan.getByAddress(deleteId)==null);
		
		
	}
	
	

}
