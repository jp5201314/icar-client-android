package cn.icarowner.icarowner.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cn.icarowner.icarowner.baseinterface.IComponentContainer;
import cn.icarowner.icarowner.baseinterface.ILifeCycleComponent;

/**
 * LifeCycleComponentManager
 * create by 崔婧
 * create at 2017/5/18 下午1:37
 */
public class LifeCycleComponentManager implements IComponentContainer {

    private HashMap<String, ILifeCycleComponent> mComponentList;

    public LifeCycleComponentManager() {
    }

    /**
     * Try to add component to container
     *
     * @param component
     * @param matrixContainer
     */
    public static void tryAddComponentToContainer(ILifeCycleComponent component, Object matrixContainer) {
        tryAddComponentToContainer(component, matrixContainer, true);
    }

    public static boolean tryAddComponentToContainer(ILifeCycleComponent component, Object matrixContainer, boolean throwEx) {
        if (matrixContainer instanceof IComponentContainer) {
            ((IComponentContainer) matrixContainer).addComponent(component);
            return true;
        } else {
            if (throwEx) {
                throw new IllegalArgumentException("componentContainerContext should implements IComponentContainer");
            }
            return false;
        }
    }

    public void addComponent(ILifeCycleComponent component) {
        if (component != null) {
            if (mComponentList == null) {
                mComponentList = new HashMap<String, ILifeCycleComponent>();
            }
            mComponentList.put(component.toString(), component);
        }
    }

    public void onBecomesVisibleFromTotallyInvisible() {

        if (mComponentList == null) {
            return;
        }

        Iterator<Entry<String, ILifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            ILifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomesVisibleFromTotallyInvisible();
            }
        }
    }

    public void onBecomesTotallyInvisible() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, ILifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            ILifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomesTotallyInvisible();
            }
        }
    }

    public void onBecomesPartiallyInvisible() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, ILifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            ILifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomesPartiallyInvisible();
            }
        }
    }

    public void onBecomesVisibleFromPartiallyInvisible() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, ILifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            ILifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onBecomesVisible();
            }
        }
    }

    public void onDestroy() {
        if (mComponentList == null) {
            return;
        }
        Iterator<Entry<String, ILifeCycleComponent>> it = mComponentList.entrySet().iterator();
        while (it.hasNext()) {
            ILifeCycleComponent component = it.next().getValue();
            if (component != null) {
                component.onDestroy();
            }
        }
    }
}
