package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Named("asset_log")
public class AssetLog extends Model
{
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	public String description;

	public String last_access_by;

	@Formats.DateTime(pattern="yyyy-MM-dd")
	public Date last_access_date;

	// Generic query helper for entity
	public static Find<Long, AssetLog> find = new Find<Long, AssetLog>()
	{
	};

	public static Map<String,String> options(){
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for(AssetLog c: AssetLog.find.orderBy("name").findList()) {
			options.put(c.id.toString(), c.name);
		}
		return options;
	}

	public static PagedList<AssetLog> page(int page, int pageSize) {
		return
				find
					.orderBy("last_access_date desc")
					.findPagedList(page, pageSize);
	}
}
