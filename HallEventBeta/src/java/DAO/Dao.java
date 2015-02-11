/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author chapolim
 */
public interface Dao<T> {

    public long save(T bean);

    public void update(T bean);

    public void merge(T bean);

    public void destroy(T bean);

    public T getBean(Serializable id);

    public List<T> getBeans();

    public List<T> getBeansByQuantity(int maxResult);
}
