package cn.freeexchange.gateway.domain.usertype;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;

import cn.freeexchange.common.base.usertype.BaseUserType;
import cn.freeexchange.gateway.domain.service.AdapterV1Bean;
import cn.freeexchange.gateway.domain.service.AdapterV1Hessian;
import cn.freeexchange.gateway.domain.service.AdapterV2Bean;
import cn.freeexchange.gateway.domain.service.AdapterV2Hessian;
import cn.freeexchange.gateway.domain.service.HttpHandler;
import cn.freeexchange.gateway.domain.service.MockHandler;
import cn.freeexchange.gateway.domain.service.ServiceHandler;

public class ServiceHandlerUT extends BaseUserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{StandardBasicTypes.INTEGER.sqlType(), StandardBasicTypes.STRING.sqlType()};
	}

	@Override
	public Class returnedClass() {
		return ServiceHandler.class;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		int adapterType = rs.getInt(names[0]);
        String value = rs.getString(names[1]);
        switch (adapterType) {
            case ServiceHandler.TYPE_MOCK:
                return new MockHandler(value);
            case ServiceHandler.TYPE_V1_HESSIAN:
                return new AdapterV1Hessian(value);
            case ServiceHandler.TYPE_V2_HESSIAN:
                return new AdapterV2Hessian(value);
            case ServiceHandler.TYPE_V1_BEAN:
                return new AdapterV1Bean(value);
            case ServiceHandler.TYPE_V2_BEAN:
                return new AdapterV2Bean(value);
            case ServiceHandler.TYPE_V1_HTTP:
                return new HttpHandler(value);
            default:
                throw new RuntimeException("未知的适配器类型[" + adapterType + "]");
        }
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		
	}

	@Override
	public void setParameterValues(Properties parameters) {
		
	}
	
}
