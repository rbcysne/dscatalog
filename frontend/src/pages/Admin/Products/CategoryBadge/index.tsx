import './styles.css';

type Props = {
    categoryName: string;
}

const CategoryBadge = ( {categoryName} : Props ) => {
    return (
        <div className="product-badge-container">
            {categoryName}
        </div>
    );
}

export default CategoryBadge;