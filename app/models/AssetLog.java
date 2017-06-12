package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.inject.Named;
import javax.persistence.*;
import java.util.Date;

@Entity
@Named("asset_log")
public class AssetLog extends Model
{
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "asset_id")
	public Asset asset;

	@Constraints.Required
	public String username;

	@Constraints.Required
	@Formats.DateTime(pattern="yyyy-MM-dd")
	public Date access_date;

	// Generic query helper for entity
	public static Find<Long, AssetLog> find = new Find<Long, AssetLog>()
	{
	};

	public static PagedList<AssetLog> page(int page, int pageSize) {
		return
				find
					.orderBy("access_date desc")
					.findPagedList(page, pageSize);
	}
}
