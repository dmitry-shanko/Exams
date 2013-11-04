package by.epam.web.taglibrary;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * This tag is used to check if the "bean" attribute from pageContext is instanceof "classtype" attribute.
 * @author Dmitry Shanko
 *
 */
public class InstanceTag extends BodyTagSupport 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classtype;
	private Object bean;

	public void setBean(String bean) throws JspException
	{
		if (null != bean)
		{
			this.bean = pageContext.findAttribute(bean);
		}
		else
		{
			throw new JspException("bean attribute is required");
		}
	}

	public void setClasstype(String classtype)
	{	
		if (null != classtype)
		{
			this.classtype = classtype;
		}
		else
		{
			this.classtype = "";
		}
	}
	/**
	 * If bean can be casted to the classtype return EVAL_BODY_INCLUDE. In case of Exception return SKIP_BODY.
	 */
	@Override
	public int doStartTag() throws JspException 
	{
		try
		{
			Class<?> cl = Class.forName(classtype);
			cl.cast(bean);
			return EVAL_BODY_INCLUDE;
		}
		catch(ClassCastException e)
		{
			return SKIP_BODY;
		} 
		catch (ClassNotFoundException e) 
		{
			throw new JspException("incorret value of attribute classtype");
		}
	}	
}

