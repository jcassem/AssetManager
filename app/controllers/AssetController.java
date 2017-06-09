package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Asset;
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
		return ok(views.html.list.render(Asset.page(page, 20, sortBy, order, filter), sortBy, order, filter));
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

		return GO_HOME;
	}
}
