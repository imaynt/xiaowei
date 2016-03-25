package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

public class JoinManyToDateEntityTest extends AbstractDaoTestLongPk<JoinManyToDateEntityDao, JoinManyToDateEntity> {

    public JoinManyToDateEntityTest() {
        super(JoinManyToDateEntityDao.class);
    }

    @Override
    protected JoinManyToDateEntity createEntity(Long key) {
        JoinManyToDateEntity entity = new JoinManyToDateEntity();
        entity.setId(key);
        return entity;
    }

}
