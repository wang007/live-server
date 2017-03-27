package org.live.common.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 *  实体基类，项目中的实体都需要继承该类
 * @author  Mr.wang
 *
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -2656053215297006265L;

	@Id
    @GenericGenerator(name="idGenerator", strategy="org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator="idGenerator")
    @Column(name = "id")
    protected  String id ;

    @Version
    protected long version = 1 ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
