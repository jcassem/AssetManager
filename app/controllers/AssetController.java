package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Asset;
import models.AssetLog;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

public class AssetController extends Controller
{
	public Result GO_HOME = Results.redirect(routes.AssetController.list(0, "id", "asc", ""));
	private FormFactory formFactory;

	@Inject public AssetController(FormFactory formFactory)
	{
		this.formFactory = formFactory;
	}

	public Result index()
	{
		return GO_HOME;
	}

	public Result list(int page, String sortBy, String order, String filter)
	{
		return ok(views.html.assetlist.render(Asset.page(page, 20, sortBy, order, filter), sortBy, order, filter));
	}

	public Result view(Long id)
	{
		Form<Asset> assetForm = formFactory.form(Asset.class).fill(Asset.find.byId(id));
		return ok(views.html.viewasset.render(assetForm.get()));
	}

	public Result edit(Long id)
	{
		Form<Asset> assetForm = formFactory.form(Asset.class).fill(Asset.find.byId(id));
		return ok(views.html.edit.render(id, assetForm));
	}

	public Result update(Long id)
	{
		Form<Asset> assetForm = formFactory.form(Asset.class).bindFromRequest();
		if (assetForm.hasErrors())
		{
			return badRequest(views.html.edit.render(id, assetForm));
		}

		Transaction transaction = Ebean.beginTransaction();
		try
		{
			Asset savedAsset = Asset.find.byId(id);
			if (savedAsset != null)
			{
				Asset updatedAsset = assetForm.get();

				savedAsset.name = updatedAsset.name;
				savedAsset.description = updatedAsset.description;
				savedAsset.last_access_by = updatedAsset.last_access_by;
				savedAsset.last_access_date = updatedAsset.last_access_date;

				savedAsset.update();
				transaction.commit();
			}
		}
		finally
		{
			transaction.end();
		}

		return view(id);
	}

	public Result create()
	{
		return ok(views.html.create.render(formFactory.form(Asset.class)));
	}

	public Result save()
	{
		Form<Asset> assetForm = formFactory.form(Asset.class).bindFromRequest();
		if (assetForm.hasErrors())
		{
			return GO_HOME;
		}

		assetForm.get().save();

		return ok(views.html.viewasset.render(assetForm.get()));
	}

	public Result delete(Long id)
	{
		return TODO;
	}

	public Result createLog(Long assetId)
	{
		return ok(views.html.createlog.render(assetId, formFactory.form(AssetLog.class)));
	}

	public Result saveLog()
	{
		Form<AssetLog> assetLogForm = formFactory.form(AssetLog.class).bindFromRequest();
		if (assetLogForm.hasErrors())
		{
			return GO_HOME;
		}

		assetLogForm.get().save();

		Transaction transaction = Ebean.beginTransaction();
		try
		{
			Asset savedAsset = assetLogForm.get().asset;
			if (savedAsset != null)
			{
				savedAsset.last_access_by = assetLogForm.get().username;
				savedAsset.last_access_date = assetLogForm.get().access_date;

				savedAsset.update();
				transaction.commit();
			}
		}
		finally
		{
			transaction.end();
		}

		return ok(views.html.viewasset.render(assetLogForm.get().asset));
	}
}
