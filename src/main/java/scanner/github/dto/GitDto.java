package scanner.github.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GitDto {

	private static final String GITHUB_API_BASE_URL = "https://api.github.com";

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

	// @Todo - modify if camel case could applied.
	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RepositoryInfo {
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

		@Builder
		public RepositoryInfo(Long id, String node_id, String full_name, String visibility, String created_at,
			String updated_at, String url, String downloads_url, String default_branch, Permission permissions) {
			this.id = id;
			this.node_id = node_id;
			this.full_name = full_name;
			this.visibility = visibility;
			this.created_at = created_at;
			this.updated_at = updated_at;
			this.url = url;
			this.downloads_url = downloads_url;
			this.default_branch = default_branch;
			this.permissions = permissions;
		}

		public static RepositoryInfo toApiRepository(final RepositoryInfo repo) {
			return RepositoryInfo.builder()
				.id(repo.getId())
				.node_id(repo.getNode_id())
				.full_name(repo.getFull_name())
				.visibility(repo.getVisibility())
				.created_at(repo.getCreated_at())
				.updated_at(repo.getUpdated_at())
				.url(repo.getUrl().replace(GITHUB_API_BASE_URL, ""))
				.downloads_url(repo.getDownloads_url().replace(GITHUB_API_BASE_URL, ""))
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
			this.url = url.replace(GITHUB_API_BASE_URL, "");
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Commit {

	}
}
