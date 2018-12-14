package cn.freeexchange.gateway.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.freeexchange.common.base.utils.CollectionUtils;


public class SupportElements {

	/**
     * 支持的元素
     */
    private Set<String> supportElementsList = new HashSet<String>();

    /**
     * 不支持的元素
     */
    private Set<String> notSupportElementsList = new HashSet<String>();

    /**
     * 支持全部元素
     */
    private Boolean supportAll = false;

	/* (non-Javadoc)
     * @see com.shengpay.bgw.commons.valueobject.SupportElements#isSupport(java.lang.String)
	 */

    public boolean isSupport(String aElement) {
        if (Boolean.TRUE.equals(supportAll)) {
            return true;
        }

        String ele = aElement != null ? aElement.toLowerCase() : null;
        if (notSupportElementsList.size() > 0) {
            return !notSupportElementsList.contains(ele);
        }

        return supportElementsList.contains(ele);
    }

    public boolean notSupport(String aElement) {
        return !isSupport(aElement);
    }

    public SupportElements merge(SupportElements elements) {
        SupportElements newElements = new SupportElements();
        newElements.supportAll = this.supportAll || elements.supportAll;
        newElements.supportElementsList.addAll(this.supportElementsList);
        newElements.supportElementsList.addAll(elements.supportElementsList);
        newElements.notSupportElementsList.addAll(this.notSupportElementsList);
        newElements.notSupportElementsList.addAll(elements.notSupportElementsList);
        return newElements;
    }

    public boolean isInclude(SupportElements other) {
        if (supportAll) return true;
        if (other.supportAll) return false;

        if (!notSupportElementsList.isEmpty()) {
            for (String otherSupport : other.supportElementsList) {
                if (notSupportElementsList.contains(otherSupport)) {
                    return false;
                }
            }
        }
        if (!other.notSupportElementsList.isEmpty())
            return notSupportElementsList.containsAll(other.notSupportElementsList);

        return supportElementsList.containsAll(other.supportElementsList);
    }


    public boolean isOverlap(SupportElements se) {
        if (!(se instanceof SupportElements)) {
            throw new RuntimeException("类型【" + se + "】不匹配！");
        }

        //如果其中一个为支持全部，则会重合
        SupportElements sei = (SupportElements) se;
        if (Boolean.TRUE.equals(supportAll) || Boolean.TRUE.equals(sei.supportAll)) {
            return true;
        }

        if (notSupportElementsList.size() > 0 && sei.notSupportElementsList.size() > 0) {
            return true;
        }

        if (notSupportElementsList.size() > 0) {
            return !notSupportElementsList.equals(sei.supportElementsList);
        }

        if (sei.notSupportElementsList.size() > 0) {
            return !sei.notSupportElementsList.equals(supportElementsList);
        }

        return CollectionUtils.isOverlap(supportElementsList, sei.supportElementsList) ||
        		CollectionUtils.isOverlap(sei.supportElementsList, supportElementsList);
    }


    public boolean isNull() {
        return supportAll == null && supportElementsList.size() == 0 && notSupportElementsList.size() == 0;
    }


    public Collection<String> getSupportElementsList() {
        return Collections.unmodifiableCollection(supportElementsList);
    }

    public void setSupportElementsList(Set<String> supportElementsList) {
        if (supportElementsList != null) {
            this.supportElementsList = CollectionUtils.toLowerCase(supportElementsList);
        } else {
            this.supportElementsList = new HashSet<String>();
        }
    }

    public void addSupportElement(String element) {
        this.supportElementsList.add(StringUtils.lowerCase(element));
    }

    public void addNotSupportElement(String element) {
        this.notSupportElementsList.add(StringUtils.lowerCase(element));
    }


    public Collection<String> getNotSupportElementsList() {
        return Collections.unmodifiableCollection(notSupportElementsList);
    }

    public void setNotSupportElementsList(Set<String> notSupportElementsList) {
        if (notSupportElementsList != null) {
            this.notSupportElementsList = CollectionUtils.toLowerCase(notSupportElementsList);
        } else {
            this.notSupportElementsList = new HashSet<String>();
        }
    }


    public Boolean getSupportAll() {
        return supportAll;
    }

    public void setSupportAll(Boolean supportAll) {
        this.supportAll = supportAll;
    }
    
}
