/*
 * This source is not MIT, Please do not share it on internet
 */
package dcnet.tutorial.c01.present;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * IndexViewController
 *
 * @author F
 */
@Controller
public class IndexViewController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("frame");
        mav.addObject("G_PAGE", "parts/index");
        return mav;
    }

    @RequestMapping(value = "/b-sheet", method = RequestMethod.GET)
    public ModelAndView renderBGrid() {
        ModelAndView mav = new ModelAndView("frame");
        mav.addObject("G_PAGE", "parts/b-sheet");
        return mav;
    }

}
