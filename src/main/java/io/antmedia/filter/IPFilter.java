package io.antmedia.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.antmedia.cluster.ClusterNode;
import io.antmedia.cluster.IClusterNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.antmedia.AppSettings;
import org.springframework.context.ApplicationContext;


public class IPFilter extends AbstractFilter {

	protected static Logger log = LoggerFactory.getLogger(IPFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (isAllowed(request.getRemoteAddr()) || isComingFromCluser(request.getRemoteAddr())) {
			chain.doFilter(request, response);
			return;
		}
		
		if(((HttpServletRequest)request).getRequestURL().toString().contains("rest/v2/acm")) {
			chain.doFilter(request, response);
			return;
		}
		((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Not allowed IP");
	}
	

	public  boolean isComingFromCluser(String originAddress) 
	{
		ApplicationContext context = getAppContext();
		boolean isCluster = context.containsBean(IClusterNotifier.BEAN_NAME);
		boolean isClusterNode = false;
		if(isCluster){
			IClusterNotifier clusterNotifier = (IClusterNotifier) context.getBean(IClusterNotifier.BEAN_NAME);
			isClusterNode = clusterNotifier.getClusterStore().getClusterNodeFromIP(originAddress) != null;
		}
		return isClusterNode;
	}



	/**
	 * Test if a remote's IP address is allowed to proceed.
	 *
	 * @param remoteIPAdrress The remote's IP address, as a string
	 * @return true if allowed
	 */
	public boolean isAllowed(final String remoteIPAdrress) {
		AppSettings appSettings = getAppSettings();
		boolean result = false;
		if(appSettings != null) 
		{
			if (appSettings.isIpFilterEnabled()) {
				result = checkCIDRList(appSettings.getAllowedCIDRList(),remoteIPAdrress);
			}
			else {
				result = true;
			}
		}
		
		return result;
	}
}
