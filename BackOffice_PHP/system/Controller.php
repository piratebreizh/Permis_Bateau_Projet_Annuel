<?php

namespace FSF;

use FSF\Routing\Request;
use FSF\View;

class Controller
{
    /** @var  Request */
    protected $request;
    /** @var  View */
    protected $view;

    /**
     * @param Request $request
     */
    public function __construct($request)
    {
        $this->request = $request;
    }

    /**
     * @return Request
     */
    public function getRequest()
    {
        return $this->request;
    }

    public function getView()
    {
        if (!$this->view) {
            $this->view = new View();
            //TODO Load Default CSS and JS
        }

        $this->view->setViewPath(
            implode(
                DIRECTORY_SEPARATOR,
                array(__DIR__, '..', 'application', APPLICATION, 'view', 'layout', 'default.phtml')
            )
        );

        return $this->view;
    }
}