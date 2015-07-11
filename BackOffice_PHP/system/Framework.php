<?php

namespace FSF;

use FSF\Routing\Route\Error;
use FSF\Routing\Router;

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
            $route = $this->getRouteNotFound();

        $route->run();
    }

    public function getRouteNotFound()
    {
        $errorRoute = new Error();
        $errorRoute
            ->setRequest($this->request)
            ->setAction(Error::ERROR_404);

        return $errorRoute;
    }
}