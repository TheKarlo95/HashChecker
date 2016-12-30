package hr.karlovrbic.hashchecker.dao;

import hr.karlovrbic.hashchecker.dao.models.WebPage;

/**
 * Blueprint for other DAOs(Data Access Objects).
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
interface IDAO {

    WebPage save(WebPage webPage);

    WebPage load(String url);
}
