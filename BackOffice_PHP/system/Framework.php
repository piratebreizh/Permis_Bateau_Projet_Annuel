<?php

namespace FSF;

use FSF\Routing\Router;
use FSF\Routing\Request;
use FSF\Routing\Response;

class Framework
{

    /** @var Router */
    private $router;

    /** @var  Request */
    private $request;

    /** @var  Response */
    private $response;

    /**
     * @param Router $router
     *
     * @return $this
     */
    public function setRouter(Router $router)
    {
        $this->router = $router;

        return $this;
    }

    /**
     * @return Router
     */
    public function getRouter()
    {
        if (!$this->router) {
            $this->router = new Router();
        }

        return $this->router;
    }

    /**
     * @return Request
     */
    public function getRequest()
    {
        if (!$this->request) {
            $this->request = new Request();
        }

        return $this->request;
    }

    /**
     * @param Request $request
     *
     * @return $this
     */
    public function setRequest(Request $request)
    {
        $this->request = $request;

        return $this;
    }

    /**
     * @return Response
     */
    public function getResponse()
    {
        if (!$this->response) {
            $this->response = new Response();
        }

        return $this->response;
    }

    /**
     * @param Response $response
     *
     * @return $this
     */
    public function setResponse(Response $response)
    {
        $this->response = $response;

        return $this;
    }

    public function initialize()
    {
        //TODO Instanciate a Logger
        $this->setRequest($this->getRequest());

        $this->dispatch();
    }

    public function dispatch()
    {
        $route = $this->router->getRoute($this->request);

        if ($route == null)
            $this->getRouteError();

        $route->run();
    }

    public function getRouteError()
    {
        throw new \FSF\Error\NotFoundError();
    }
}