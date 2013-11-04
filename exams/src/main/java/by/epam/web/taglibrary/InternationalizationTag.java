package by.epam.web.taglibrary;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * This tag is used to concat String "" with any other values from "intvalue".<p>
 * This tag can be used to concat some key with id of entities from database to get propper key of Internationalized resources from pages.properties
 * @author Dmitry Shanko
 *
 */
public class InternationalizationTag extends BodyTagSupport 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String intvalue;
	private String key;

	public void setIntvalue(String intvalue) throws JspException
	{
		if (null != intvalue)
		{
			this.intvalue = intvalue;
		}
		else
		{
			this.intvalue = "";
		}
	}

	public void setKey(String key)
	{	
		if (null != key)
		{
			this.key = key;
		}
		else
		{
			this.key = "";
		}
	}
	/**
	 * Do pageContext.setAttribute("key", this.key.concat(intvalue).trim()). Result String value can be accessed only by key "key".
	 */
	@Override
	public int doStartTag() throws JspException 
	{
		pageContext.setAttribute("key", this.key.concat(intvalue).trim());
		return SKIP_BODY;
	}	
}
