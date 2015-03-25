/**
 * 
 */
package edu.pti.students.itp262.bap.servlet.webutil;


/**
 * Models an HTML page element's basic form.
 * 
 * @author Bridger Maskrey
 * @version 1.0.0
 */
public interface IHtmlElement
{
	/**
	 * Gets the HTML representation of this object.
	 * @return The HTML representation of this object.
	 */
	String buildHtml();
}
