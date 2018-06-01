package pfb.onecode.api.base.service;

import pfb.onecode.api.base.dao.CrudDao;
import pfb.onecode.api.base.entity.CrudEntity;

public abstract class CrudService<D extends CrudDao<T>,T extends CrudEntity<T>>extends BaseService{

}
