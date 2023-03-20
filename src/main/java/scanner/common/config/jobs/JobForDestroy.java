package scanner.common.config.jobs;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class JobForDestroy {

	@PreDestroy
	public void destroy() {
		//
	}
}
