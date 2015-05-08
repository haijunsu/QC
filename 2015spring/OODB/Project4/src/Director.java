import java.util.*;

import javax.jdo.*;

@javax.jdo.annotations.PersistenceCapable

public class Director extends Person
{

	public static Director find(String name, PersistenceManager pm)

	/* Returns a director with the given "name";
           if no such director exists, null is returned. 
           The function is applied to the database held by the persistence manager "pm". */

	{
		Query q = pm.newQuery(Director.class);
		q.declareParameters("String sName");
		q.setFilter("this.name == sName");

        Collection<Director> ss = (Collection<Director>) q.execute(name);
        Director d = Utility.extract(ss);
        return d;

	}

	public Collection<Movie> movies(Query q)

	/* Returns the collection of all movies directed by this director. 
	   Represents the inverse of Movie.director.
	   Sort the result by (title, releaseYear). */

	{
		q.setClass(Movie.class);
		q.declareParameters("Director d");
		q.setFilter("this.director == d");
		q.setOrdering("this.title ascending, this.releaseYear ascending");
		return (Collection<Movie>) q.execute(this);

	}

	public Collection<Studio> studiosWithThisDirector(Query q)

	/* Returns the collection of all studios that have made at least two movies
	   directed by this director.
	   Sort the result by studio name. */

	{
		q.setClass(Studio.class);
		q.setOrdering("this.name ascending");
		q.declareParameters("Director d");
		q.declareVariables("Movie m1, m2");
		q.setFilter("this.movies.contains(m1) && m1.director == d "
				+ "&& this.movies.contains(m2) && m2.director == d"
				+ "&& m1 != m2");
		
		return (Collection<Studio>) q.execute(this);

	}
}
