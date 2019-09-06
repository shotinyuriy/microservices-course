package com.gridu.microservice.taxes.integration;

import com.gridu.microservice.taxes.dao.StateDao;
import com.gridu.microservice.taxes.dao.StateRuleDao;
import com.gridu.microservice.taxes.dao.TaxCategoryDao;
import com.gridu.microservice.taxes.model.State;
import com.gridu.microservice.taxes.model.StateRule;
import com.gridu.microservice.taxes.model.TaxCategory;
import com.gridu.microservice.taxes.model.TaxRule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("entity-manager")
public class EntityManagerDaosIntegrationTest {

	@Autowired
	protected StateDao stateDao;

	@Autowired
	protected TaxCategoryDao taxCategoryDao;

	@Autowired
	protected StateRuleDao stateRuleDao;

	@Test
	public void testStateDaoCRUD() {

		State existingState = stateDao.findByCode("TX");
		if (existingState != null) {
			stateDao.remove(existingState);
		}

		// CREATE
		int sizeBefore = stateDao.getAll().size();
		State state = new State("TX", "Texass");
		state = stateDao.save(state);

		Assert.assertNotNull(state);
		Assert.assertNotNull(state.getId());
		int sizeAfterAdd = stateDao.getAll().size();
		Assert.assertEquals(sizeBefore + 1, sizeAfterAdd);

		// READ
		State texasState = stateDao.findByCode("TX");
		Assert.assertNotNull(texasState);
		Assert.assertEquals("Texass", texasState.getName());

		// UPDATE
		texasState.setName("Texas");
		stateDao.save(texasState);
		texasState = stateDao.findByCode("TX");
		Assert.assertNotNull(texasState);
		Assert.assertEquals("Texas", texasState.getName());

		int sizeAfterUpdate = stateDao.getAll().size();
		Assert.assertEquals(sizeAfterAdd, sizeAfterUpdate);

		// DELETE
		State removedState = stateDao.remove(state);
		Assert.assertNotNull(removedState);

		int sizeAfterRemoval = stateDao.getAll().size();
		Assert.assertEquals(sizeBefore, sizeAfterRemoval);
	}

	@Test
	public void testTaxCategoryCRUD() {
		final String NEW_CATEGORY = "new category";
		final String NEW_CATEGORY_2 = "new category 2";

		TaxCategory existingTaxCategory = taxCategoryDao.findByCategory(NEW_CATEGORY);
		if (existingTaxCategory != null) {
			taxCategoryDao.remove(existingTaxCategory);
		}

		TaxCategory existingTaxCategory2 = taxCategoryDao.findByCategory(NEW_CATEGORY_2);
		if (existingTaxCategory2 != null) {
			taxCategoryDao.remove(existingTaxCategory2);
		}

		// CREATE
		int sizeBefore = taxCategoryDao.getAll().size();
		TaxCategory taxCategory = new TaxCategory(NEW_CATEGORY);
		taxCategory = taxCategoryDao.save(taxCategory);

		Assert.assertNotNull(taxCategory);
		Assert.assertNotNull(taxCategory.getId());
		int sizeAfterAdd = taxCategoryDao.getAll().size();
		Assert.assertEquals(sizeBefore + 1, sizeAfterAdd);

		// READ
		TaxCategory newCategory = taxCategoryDao.findByCategory(NEW_CATEGORY);
		Assert.assertNotNull(newCategory);
		Assert.assertEquals(NEW_CATEGORY, newCategory.getName());

		// UPDATE
		newCategory.setName(NEW_CATEGORY_2);
		taxCategoryDao.save(newCategory);
		newCategory = taxCategoryDao.findByCategory(NEW_CATEGORY_2);
		Assert.assertNotNull(newCategory);
		Assert.assertEquals(NEW_CATEGORY_2, newCategory.getName());

		int sizeAfterUpdate = taxCategoryDao.getAll().size();
		Assert.assertEquals(sizeAfterAdd, sizeAfterUpdate);

		// DELETE
		TaxCategory removedCategory = taxCategoryDao.remove(newCategory);
		Assert.assertNotNull(removedCategory);

		int sizeAfterRemoval = taxCategoryDao.getAll().size();
		Assert.assertEquals(sizeBefore, sizeAfterRemoval);
	}

	@Test
	public void testSateRuleCRUD() {
		StateRule existingStateRule = stateRuleDao.findByCode("NJ");
		if (existingStateRule != null) {
			stateRuleDao.remove(existingStateRule);
		}

		// CREATE
		int sizeBefore = stateRuleDao.getAll().size();
		StateRule newStateRule = new StateRule(stateDao.findByCode("NJ"));
		newStateRule.addTaxRule(new TaxRule(taxCategoryDao.findById(1l), 0.12));
		newStateRule = stateRuleDao.save(newStateRule);
		int sizeAfterAdd = stateRuleDao.getAll().size();
		Assert.assertEquals(sizeBefore + 1, sizeAfterAdd);

		// READ
		StateRule stateRuleNj = stateRuleDao.findByCode("NJ");
		Assert.assertNotNull(stateRuleNj);

		// UPDATE
		newStateRule.addTaxRule(new TaxRule(taxCategoryDao.findById(2l), 0.22));
		stateRuleDao.save(stateRuleNj);

		int sizeAfterUpdate = stateRuleDao.getAll().size();
		Assert.assertEquals(sizeAfterAdd, sizeAfterUpdate);

		// DELETE
		StateRule removedStateRule = stateRuleDao.remove(stateRuleNj);
		Assert.assertNotNull(removedStateRule);

		int sizeAfterRemoval = stateRuleDao.getAll().size();
		Assert.assertEquals(sizeBefore, sizeAfterRemoval);
	}
}
