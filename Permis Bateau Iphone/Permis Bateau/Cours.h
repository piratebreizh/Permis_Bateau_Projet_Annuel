//
//  Cours.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 27/04/2015.
//  Copyright (c) 2015 Alexandre Dubois. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Theme;

@interface Cours : NSManagedObject

@property (nonatomic, retain) NSNumber * id;
@property (nonatomic, retain) NSString * nomPDF;
@property (nonatomic, retain) NSString * nomCours;
@property (nonatomic, retain) Theme *theme;

@end
