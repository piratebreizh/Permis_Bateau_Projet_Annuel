//
//  ViewListeDeroulanteExamenThematique.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//


#import <UIKit/UIKit.h>
#import "ViewListeDeroulanteCours.h"
#include "Cours.h"




@interface ViewListeDeroulanteCours : UITableViewController

@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;
@property (nonatomic,strong) NSArray* listeCours;
@property (strong, nonatomic) Cours *themeCours;




@end


