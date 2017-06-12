package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import org.springframework.core.annotation.Order;
import play.data.format.Formats;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Asset extends Model
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

	@OneToMany(mappedBy = "asset")
	@OrderBy("access_date desc")
	public List<AssetLog> assetLogs;

	// Generic query helper for entity
	public static Find<Long, Asset> find = new Find<Long, Asset>()
	{
	};

	public static Map<String,String> options(){
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		for(Asset c: Asset.find.orderBy("name").findList()) {
			options.put(c.id.toString(), c.name);
		}
		return options;
	}
	public static PagedList<Asset> page(int page, int pageSize, String sortBy, String order, String filter) {
		return
				find.where()
					.ilike("name", "%" + filter + "%")
					.orderBy(sortBy + " " + order)
					.findPagedList(page, pageSize);
	}

}
