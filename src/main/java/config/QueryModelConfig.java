package config;


import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 15:51
 * @Description:
 */
public class QueryModelConfig {

	public String driverName;
	public String connectURL;
	public String username;
	public String password;

	public QueryModelConfig() {
		try {
			String filePath = QueryModelConfig.class.getClassLoader()
					.getResource("query_model_db.xml").getPath();
			SAXBuilder builder = new SAXBuilder(false);
			Document document;
			document = builder.build(filePath);
			Element rootElement = document.getRootElement();
			Element config = rootElement.getChild("connectConfig");
			String username = config.getChildText("username");
			String password = config.getChildText("password");
			String driverName = config.getChildText("driverName");
			String connectionUrl = config.getChildText("connectionUrl");
			this.driverName = driverName;
			this.username = username;
			this.password = password;
			this.connectURL = connectionUrl;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
}
