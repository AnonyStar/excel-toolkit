package org.toolkit.repository;

import org.springframework.data.repository.CrudRepository;
import org.toolkit.model.DemoEntity;

/**
 * @author: zhoucx
 * @time: 2021-06-30
 */
public interface DemoEntityRepository extends CrudRepository<DemoEntity,Long> {
}
