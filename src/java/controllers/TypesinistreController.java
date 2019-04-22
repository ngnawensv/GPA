package controllers;

import entities.Typesinistre;
import controllers.util.JsfUtil;
import controllers.util.JsfUtil.PersistAction;
import sessions.TypesinistreFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("typesinistreController")
@SessionScoped
public class TypesinistreController implements Serializable {

    @EJB
    private sessions.TypesinistreFacade ejbFacade;
    private List<Typesinistre> items = null;
    private Typesinistre selected;

    public TypesinistreController() {
    }

    public Typesinistre getSelected() {
        return selected;
    }

    public void setSelected(Typesinistre selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TypesinistreFacade getFacade() {
        return ejbFacade;
    }

    public Typesinistre prepareCreate() {
        selected = new Typesinistre();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/msg").getString("TypesinistreCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/msg").getString("TypesinistreUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/msg").getString("TypesinistreDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Typesinistre> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/msg").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/msg").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Typesinistre getTypesinistre(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Typesinistre> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Typesinistre> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Typesinistre.class)
    public static class TypesinistreControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TypesinistreController controller = (TypesinistreController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "typesinistreController");
            return controller.getTypesinistre(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Typesinistre) {
                Typesinistre o = (Typesinistre) object;
                return getStringKey(o.getIdTypesinistre());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Typesinistre.class.getName()});
                return null;
            }
        }

    }

}
