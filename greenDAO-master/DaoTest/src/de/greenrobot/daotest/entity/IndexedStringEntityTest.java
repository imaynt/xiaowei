package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

public class IndexedStringEntityTest extends AbstractDaoTestLongPk<IndexedStringEntityDao, IndexedStringEntity> {

    public IndexedStringEntityTest() {
        super(IndexedStringEntityDao.class);
    }

    @Override
    protected IndexedStringEntity createEntity(Long key) {
        IndexedStringEntity entity = new IndexedStringEntity();
        entity.setId(key);
        return entity;
    }

}
