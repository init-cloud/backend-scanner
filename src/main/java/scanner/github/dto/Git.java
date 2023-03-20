package scanner.github.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Git {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Permission {
		private Boolean admin;
		private Boolean maintain;
		private Boolean push;
		private Boolean triage;
		private Boolean pull;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Repository {
		private Long id;
		private String node_id;
		private String full_name;
		private String visibility;
		private String created_at;
		private String updated_at;
		private String url;
		private String downloads_url;
		private String default_branch;
		private Permission permissions;

		// @Todo - modify if camel case could applied.
		public static Repository toRepository(final Repository repo) {
			return Repository.builder()
				.id(repo.getId())
				.node_id(repo.getNode_id())
				.full_name(repo.getFull_name())
				.visibility(repo.getVisibility())
				.created_at(repo.getCreated_at())
				.updated_at(repo.getUpdated_at())
				.url(repo.getUrl().replace("https://api.github.com", ""))
				.downloads_url(repo.getDownloads_url().replace("https://api.github.com", ""))
				.default_branch(repo.getDefault_branch())
				.permissions(repo.getPermissions())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class File {
		private String type;
		private String name;
		private String sha;
		private String path;
		private String url;

		public File(String type, String name, String sha, String path, String url) {
			this.type = type;
			this.name = name;
			this.sha = sha;
			this.path = path;
			this.url = url.replace("https://api.github.com", "");
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Commit {

	}
}
