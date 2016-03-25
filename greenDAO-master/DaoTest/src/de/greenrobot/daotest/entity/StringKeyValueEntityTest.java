package de.greenrobot.daotest.entity;

import junit.framework.Assert;
import de.greenrobot.dao.test.AbstractDaoTestStringPk;

public class StringKeyValueEntityTest extends AbstractDaoTestStringPk<StringKeyValueEntityDao, StringKeyValueEntity> {

    public StringKeyValueEntityTest() {
        super(StringKeyValueEntityDao.class);
    }

    @Override
    protected StringKeyValueEntity createEntity(String key) {
        StringKeyValueEntity entity = new StringKeyValueEntity();
        entity.setKey(key);
        return entity;
    }

    public void testInsertWithoutPK() {
        StringKeyValueEntity entity = createEntity(null);
        try {
            dao.insert(entity);
            Assert.fail("Insert without pre-set PK succeeded");
        } catch (Exception e) {
            // Expected
        }
    }

}
