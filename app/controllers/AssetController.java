package controllers;

import models.Asset;
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
}
