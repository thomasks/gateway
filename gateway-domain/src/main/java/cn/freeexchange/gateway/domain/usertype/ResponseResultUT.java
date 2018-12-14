package cn.freeexchange.gateway.domain.usertype;

import static cn.freeexchange.common.base.utils.ClassUtils.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import cn.freeexchange.common.base.usertype.BaseCompositeUT;
import cn.freeexchange.gateway.domain.ResponseResult;

public class ResponseResultUT extends BaseCompositeUT {
	
	public static final String[] propertyNames = {"code", "msg"};
    public static final Type[] propertyTypes = {StandardBasicTypes.STRING, StandardBasicTypes.STRING};
    
	@Override
	public String[] getPropertyNames() {
		return propertyNames;
	}

	@Override
	public Type[] getPropertyTypes() {
		return propertyTypes;
	}

	@Override
	public Object getPropertyValue(Object component, int property) throws HibernateException {
		if (component == null) return null;
        ResponseResult result = (ResponseResult) component;
        switch (property) {
            case 0:
                return result.getCode();
            case 1:
                return result.getMsg();
            default:
                throw new RuntimeException("无法取得[" + component + "]第[" + property + "]个属性");
        }
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		if (component != null) setValueToField(component, propertyNames[property], value);

	}

	@Override
	public Class returnedClass() {
		return ResponseResult.class;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		String code = rs.getString(names[0]);
        String msg = rs.getString(names[1]);
        return StringUtils.isNotBlank(code) ? new ResponseResult(code, msg) : null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value != null) {
            ResponseResult result = (ResponseResult) value;
            st.setString(index, result.getCode());
            st.setString(index + 1, result.getMsg());
        } else {
            st.setNull(index, Types.VARCHAR);
            st.setNull(index + 1, Types.VARCHAR);
        }
	}

}
