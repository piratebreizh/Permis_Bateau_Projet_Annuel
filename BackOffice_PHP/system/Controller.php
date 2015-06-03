<?php

namespace FSF;

use FSF\Request;
use FSF\Response;

class Controller
{
    /** @var  Request */
    protected $request;
    /** @var  Response */
    private $response;
    /** @var  View */
    protected $view;

    /**
     * @param Request $request
     */
    public function __construct(Request $request)
    {
        $this->request = $request;
    }

    /**
     * @return Request
     */
    public function getRequest()
    {
        if (is_null($this->request)) {
            $this->request = new Request();
        }
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

    /**
     * @return Response
     */
    public function getResponse()
    {
        if (is_null($this->response)) {
            $this->response = new Response();
        }
        return $this->response;
    }

    /**
     * @param Response $response
     * @return Controller
     */
    public function setResponse(Response $response)
    {
        $this->response = $response;

        return $this;
    }
}